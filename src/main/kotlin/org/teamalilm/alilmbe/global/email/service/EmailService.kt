package org.teamalilm.alilmbe.global.email.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    @Value("\${spring.mail.subject}") private val subject: String,
    @Value("\${spring.mail.from}") private val from: String,

    private val emailSender: JavaMailSender,
) {

    fun sendMail(message: String, to: String) {
        val mimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        helper.setFrom(from)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(message, true)
        
        emailSender.send(mimeMessage)
    }
}