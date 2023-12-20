package request;

import com.example.software_engineering_project.entity.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A data class representing the information required for creating a new payment.
 */
public class PaymentCreationRequest {
    private Payment payment;
    private Map<UUID, BigDecimal> userParticipations;


    /**
     * Constructs a new PaymentCreationData object with the specified payment.
     *
     * @param payment The payment associated with this creation data.
     */
    public PaymentCreationRequest(Payment payment){
        this.payment = payment;
        userParticipations = new HashMap();
    }

    public void PaymentCreationData(){}

    /**
     * Gets the payment associated with this creation data.
     *
     * @return The payment associated with this creation data.
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the payment associated with this creation data.
     *
     * @param payment The payment to be associated with this creation data.
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @JsonIgnore
    public Set<UUID> getParticipantUserIds() {
        return userParticipations.keySet();
    }

    /**
     * Gets the map of user participations in the payment, where the key is the user ID and the value is the participation amount.
     *
     * @return The map of user participations.
     */
    public Map<UUID, BigDecimal> getUserParticipations() { return userParticipations; }

    /**
     * Sets the map of user participations in the payment.
     *
     * @param userParticipations The map of user participations to be set.
     */
    public void setUserParticipations(Map<UUID, BigDecimal> userParticipations) { this.userParticipations = userParticipations; }
}

