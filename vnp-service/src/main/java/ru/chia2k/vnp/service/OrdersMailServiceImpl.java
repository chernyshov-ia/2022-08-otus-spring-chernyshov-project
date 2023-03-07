package ru.chia2k.vnp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.chia2k.vnp.dto.OrderDto;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersMailServiceImpl implements OrdersMailService {
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender emailSender;
    @Value("${spring.mail.from}")
    private String from;
    public static final String DEFAULT_ATTACHMENT_NAME = "Препроводительная ведомость.pdf";
    public static final String TICKET_SUBJECT_TEMPLATE = "Сформирована препроводительная ведомость по заявке ВНП №%d";
    public static final String NEW_ORDER_SUBJECT_TEMPLATE = "Создана заявка ВНП №%d";

    @Override
    public void sendNotifyNewOrder(OrderDto order) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(order.getUser().email());
            messageHelper.setFrom(from);
            messageHelper.setSubject(String.format(NEW_ORDER_SUBJECT_TEMPLATE, order.getId()));

            final Context ctx = new Context(Locale.getDefault());
            ctx.setVariable("order", order);
            String body = templateEngine.process("new_order", ctx);
            messageHelper.setText(body, true);
            emailSender.send(mimeMessage);

            log.info("Уведомление о новом заказ {} отправлено на почтовый ящик {}",
                    order.getId(),
                    order.getUser().email());
        } catch (MessagingException e) {
            log.error("{}", e);
        }
    }

    @Override
    public void sendTicket(OrderDto order, byte[] ticket, String ticketFilename) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(order.getUser().email());
            messageHelper.setFrom(from);
            messageHelper.setSubject(String.format(TICKET_SUBJECT_TEMPLATE, order.getId()));

            final Context ctx = new Context(Locale.getDefault());
            ctx.setVariable("order", order);
            String body = templateEngine.process("ticket", ctx);
            messageHelper.setText(body, true);
            ByteArrayResource resource = new ByteArrayResource(ticket);
            String filename = ticketFilename;
            if (filename == null || "".equals(filename.trim())) {
                filename = DEFAULT_ATTACHMENT_NAME;
            }
            messageHelper.addAttachment(filename, resource);
            emailSender.send(mimeMessage);

            log.info("Этикетка \"{}\" отправлена на почтовый ящик {}",
                    ticketFilename,
                    order.getUser().email());

        } catch (MessagingException e) {
            log.error("{}", e);
        }
    }
}

