package com.shoppingmate.model;

public class PaymentDetails {
    private String paymentMethod;
    private String status;
    private String paymentId;
    private String razopayPaymentLinkId;
    private String razopayPaymentLinkReferenceId;
    private String razopayPaymentLinkStatus;
    private String razopayPaymentId;

    public PaymentDetails() {
    }

    public PaymentDetails(String paymentMethod, String status, String paymentId, String razopayPaymentLinkId, String razopayPaymentLinkReferenceId, String razopayPaymentLinkStatus, String razopayPaymentId) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paymentId = paymentId;
        this.razopayPaymentLinkId = razopayPaymentLinkId;
        this.razopayPaymentLinkReferenceId = razopayPaymentLinkReferenceId;
        this.razopayPaymentLinkStatus = razopayPaymentLinkStatus;
        this.razopayPaymentId = razopayPaymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRazopayPaymentLinkId() {
        return razopayPaymentLinkId;
    }

    public void setRazopayPaymentLinkId(String razopayPaymentLinkId) {
        this.razopayPaymentLinkId = razopayPaymentLinkId;
    }

    public String getRazopayPaymentLinkReferenceId() {
        return razopayPaymentLinkReferenceId;
    }

    public void setRazopayPaymentLinkReferenceId(String razopayPaymentLinkReferenceId) {
        this.razopayPaymentLinkReferenceId = razopayPaymentLinkReferenceId;
    }

    public String getRazopayPaymentLinkStatus() {
        return razopayPaymentLinkStatus;
    }

    public void setRazopayPaymentLinkStatus(String razopayPaymentLinkStatus) {
        this.razopayPaymentLinkStatus = razopayPaymentLinkStatus;
    }

    public String getRazopayPaymentId() {
        return razopayPaymentId;
    }

    public void setRazopayPaymentId(String razopayPaymentId) {
        this.razopayPaymentId = razopayPaymentId;
    }
}
