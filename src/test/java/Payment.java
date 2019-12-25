import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    public Status status;
    public String transaction_id;
    public String payment_id;
    public String credit_id;
    public String bank_id;
    public int countID;
}