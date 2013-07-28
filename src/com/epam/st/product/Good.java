package com.epam.st.product;

public class Good {
	private String producer;
	private String model;
	private String dateOfIssue;
	private String color;
	private String price;
	private boolean notInStock;

	// the value of price field if the product not in stock
	private static final String NOT_IN_STOCK = "";

	public Good(String producer, String model, String dateOfIssue,
			String color, String price) {
		this.producer = producer;
		this.model = model;
		this.dateOfIssue = dateOfIssue;
		this.color = color;
		this.price = price;
		this.notInStock = false;
	}

	public Good(String producer, String model, String dateOfIssue,
			String color) {
		this.producer = producer;
		this.model = model;
		this.dateOfIssue = dateOfIssue;
		this.color = color;
		this.notInStock = true;
		this.price = NOT_IN_STOCK;
	}
	
	public Good() {
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
		this.notInStock = false;
	}

	public boolean isNotInStock() {
		return notInStock;
	}

	public void setNotInStock(boolean notInStock) {
		this.notInStock = notInStock;
	}
	
	public void markAsNotInStock() {
		this.notInStock = true;
		this.price = NOT_IN_STOCK;
	}

	@Override
	public int hashCode() {
		final int prime1 = 31;
		final int prime2 = 101;
		final int prime3 = 13;
		final int prime4 = 41;
		final int prime5 = 61;
		final int prime6 = 23;
		int result = 1;
		result = prime1 * result + ((color == null) ? 0 : color.hashCode());
		result = prime2 * result
				+ ((dateOfIssue == null) ? 0 : dateOfIssue.hashCode());
		result = prime3 * result + ((model == null) ? 0 : model.hashCode());
		result = prime4 * result + (notInStock ? 1231 : 1237);
		result = prime5 * result + price.hashCode();
		result = prime6 * result
				+ ((producer == null) ? 0 : producer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}	
		Good other = (Good) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (dateOfIssue == null) {
			if (other.dateOfIssue != null) {
				return false;
			}
		} else if (!dateOfIssue.equals(other.dateOfIssue)) {
			return false;
		}
		if (model == null) {
			if (other.model != null) {
				return false;
			}
		} else if (!model.equals(other.model)) {
			return false;
		}
		if (notInStock != other.notInStock) {
			return false;
		}
		if (!price.equals(other.price)) {
			return false;
		}
		if (producer == null) {
			if (other.producer != null) {
				return false;
			}
		} else if (!producer.equals(other.producer)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Good [producer=" + producer + ", model=" + model
				+ ", dateOfIssue=" + dateOfIssue + ", color=" + color
				+ ", price=" + price + ", notInStock=" + notInStock + "]";
	}
}
