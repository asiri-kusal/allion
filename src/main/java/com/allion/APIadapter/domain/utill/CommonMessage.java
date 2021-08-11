package com.allion.APIadapter.domain.utill;

public enum CommonMessage {
		SUCCESS(0, "Success"), // success response
		FAIL(1, "Fail"), // fail response
		NO_CONTENT(2, "No Content"), // no content response
		CREATED(3, "Created"), // created successfully response
		UPDATED(4, "Updated"), // updated successfully response
		BAD_REQUEST(5, "Bad Request"), // bad request response
		EXCEPTION(6, "Exception"), // exception response
		NOT_FOUND(7, "Not Found"), // not found response
		CONFLICT(8, "Conflict"); // conflict response

		private final int value; // API status code
		private String message; // related message

		// constructor for value only
		CommonMessage(int value) {
			this.value = value;
		}

		// constructor for value and message
		CommonMessage(int value, String message) {
			this.value = value;
			this.message = message;
		}

		// return the response API status code
		public int value() {
			return value;
		}

		// return the response message
		public String message() {
			return message;
		}


}
