package com.service;


import com.ItemDto;
import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    public String generateInvoice(Long userId) {
        List<Order> orders = orderService.getAllOrderByUserId(1L); // hard code
        if (orders.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        AtomicReference<Double> totalOrder = new AtomicReference<>(0.00);
        List<ItemDto> itemDtos = orders.stream().map(order -> {
            OrderDetail orderDetail = order.getOrderDetail();
            Product product = orderDetail.getProduct();
//            User user = order.getUser();
            totalOrder.updateAndGet(v -> v + orderDetail.getTotalPrice());
            return ItemDto.builder()
                    .itemName(product.getProductName())
                    .qty(orderDetail.getQty())
                    .itemPrice(product.getPrice())
                    .price(orderDetail.getTotalPrice()).build();
        }).collect(Collectors.toList());

        String filename = "INV-REPORT" + UUID.randomUUID() + ".pdf";
        String invNumber = "INV-2301-23002-AS";
        String invDate = "23-10-2023";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invDate", invDate);
        parameters.put("invNumber", invNumber);
        parameters.put("totalOrder", totalOrder.get());
        log.info("total order {} ?? {}", parameters.get("totalOrder"), totalOrder.get());

        String jasperLocation = ResourceUtils.CLASSPATH_URL_PREFIX + "Blank_A4.jasper";
        try {
            String jasperSource = ResourceUtils.getFile(jasperLocation).getAbsolutePath();
            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(itemDtos);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperSource, parameters, beanDataSource);

            String folderPath = ResourceUtils.getFile("classpath:").getAbsolutePath();
            String filePath = Paths.get(folderPath, filename).toString();
            JasperExportManager.exportReportToPdfFile(jasperPrint,filePath);
//            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
//            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=invoice-report.pdf;");
            log.info("Jasper location {}", jasperSource);
            log.info("Jasper location {}", filePath);
            return filePath;
        }catch (Exception e) {
            log.error("ERROR generateInvoice INVOICE SERVICE {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }

    }
}
