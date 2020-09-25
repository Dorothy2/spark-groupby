
public class CashAvail {
	private String fundId;
	private String cashType;
	private String cashSubType;
	private String currency;
	private Double amount;
	
	public CashAvail(String fundId, String cashType, String cashSubType, String currency, double amount) {
		super();
		this.fundId = fundId;
		this.cashType = cashType;
		this.cashSubType = cashSubType;
		this.currency = currency;
		this.amount = amount;
	}
	
	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	public String getCashType() {
		return cashType;
	}
	public void setCashType(String cashType) {
		this.cashType = cashType;
	}
	public String getCashSubType() {
		return cashSubType;
	}
	public void setCashSubType(String cashSubType) {
		this.cashSubType = cashSubType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "CashAvail [fundId=" + fundId + ", cashType=" + cashType + ", cashSubType=" + cashSubType + ", currency="
				+ currency + ", amount=" + amount + "]";
	}

}
