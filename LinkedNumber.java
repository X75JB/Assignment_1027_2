package LinkedNumbers;

/**
 * Represents a number as a doubly-linked list of digits in a specified base. This class
 * allows for operations such as adding and removing digits, converting between bases,
 * and comparing numbers for equality.
 */
public class LinkedNumber {
	private int base;
    private DLNode<Digit> rear;
    private DLNode<Digit> front;

    /**
     * Constructor that creates a LinkedNumber object from a string representation of a number 
     * and its numerical base. This allows for representing the number in any base system.
     *
     * @param num The string representation of the number. Each character in the string should 
     *            be a valid digit in the given base system.
     * @param baseNum The base of the number system for this number (EX: 2 for binary, 10 for decimal).
     * @throws LinkedNumberException if the input string is empty, indicating that no digits were provided.
     */
    public LinkedNumber(String num, int baseNum) {
        this.base = baseNum;
        // Exception.
        if (num.isEmpty()) {
            throw new LinkedNumberException("no digits given");
        }
        // Converting the string into a char array.
        for (char c : num.toCharArray()) {
            addDigitToEnd(new Digit(c));
        }
    }

    /**
     * Constructor that takes an integer and creates a LinkedNumber object 
     * representing the same integer in base 10.
     * 
     * @param num The integer to convert into a LinkedNumber.
     */
    public LinkedNumber(int num) {
        this(String.valueOf(num), 10);
    }
    
    /**
     * Adds a digit to the end of the linked list representing the number. If the list is empty, 
     * the new digit becomes both the front and rear of the list. Otherwise, it is added 
     * after the current rear and becomes the new rear.
     *
     * @param digit The digit to be added to the end of the linked list.
     */
    private void addDigitToEnd(Digit digit) {
    	// Create a new node for the digit.
    	DLNode<Digit> newNode = new DLNode<>(digit);
    	// If the list is currently empty, make it both front and rear to this new node.
    	if (front == null) {
            front = rear = newNode;
        } else {
        	// If the list is not empty, append the new node to the end of the list.
            rear.setNext(newNode);
            newNode.setPrev(rear);
            rear = newNode;
        }
    }
    
    /**
     * Checks if the number represented by the linked list is valid in its specified base. 
     * A number is considered valid if all digits are within the range of 0 to base - 1. 
     * EX: in base 10, valid digits are 0 through 9. This goes 
     * through each digit in the list and verifies its validity.
     *
     * @return returns true if the number is valid within its base, meaning all digits are 
     *         within the correct range. Returns false if any digit is invalid, 
     *         such as being negative or equal to or greater than the base.
     */
    public boolean isValidNumber() {
    	// First digit in the list.
    	DLNode<Digit> currentNode = front;
        while (currentNode != null) {
            Digit currentDigit = currentNode.getElement();
            int digitValue = currentDigit.getValue();
         // Check if the digit's value is outside the valid range for the base.
            if (digitValue == -1 || digitValue >= base) {
                // If false return false.
            	return false;
            }
            // Check next digit.
            currentNode = currentNode.getNext();
        }
        // Valid, return true.
        return true;
    }

    /**
     * Retrieves the base of the number system.
     * The base determines the numerical system (EX: binary and decimal) of the number 
     * represented by this LinkedNumber.
     *
     * @return The base of the number system. EX: 2 for binary numbers, 
     *         10 for decimal numbers, etc.
     */
    public int getBase() {
        return base;
    }

    /**
     * Retrieves the first node of the linked list representing the number.
     *
     * @return The first node of the linked list.
     */
    public DLNode<Digit> getFront() {
        return front;
    }

    /**
     * Retrieves the last node of the linked list representing the number.
     *
     * @return The last node of the linked list.
     */
    public DLNode<Digit> getRear() {
        return rear;
    }
    
    /**
     * Calculates and returns the total number of digits in the linked list representing 
     * the number. This method iterates through the entire list, counting each node until 
     * it reaches the end, to determine the number's length.
     *
     * @return The total number of digits in the linked list, which corresponds to the 
     *         length of the list.
     */
    public int getNumDigits() {
        int count = 0;
        // Front of the list.
        DLNode<Digit> currentNode = front;
        // While it is not an empty digit (the end).
        while (currentNode != null) {
            count++;
            // Next node.
            currentNode = currentNode.getNext();
        }
        // The total length of the list. EX: "ABCD" = 4.
        return count;
    }
    
    /**
     * Generates and returns a string representation of the number represented by this 
     * LinkedNumber instance. This allows for the number to be presented in a 
     * readable format.
     *
     * @return A string that represents the number in its entirety, composed of the 
     *         individual digits in order from most to least significant.
     */
    public String toString() {
        // String builder constructor.
    	StringBuilder sb = new StringBuilder();
    	// Start from the front of the list.
        DLNode<Digit> currentNode = front;
        // Go through each node till an empty digit appears (the end).
        while (currentNode != null) {
            // Appending the string.
        	sb.append(currentNode.getElement().toString());
            // Next node
        	currentNode = currentNode.getNext();
        }
        // Converting the string builder to a string then returning it.
        return sb.toString();
    }

    /**
     * Compares this LinkedNumber object with another for equality. Two LinkedNumber 
     * objects are considered equal if they represent the same number in the same base, 
     * which means they have the same sequence of digits from front to rear and are of 
     * the same base.
     *
     * @param other The other LinkedNumber object to compare with this one.
     * @return Return true if both LinkedNumber objects have the same base and 
     *         identical sequences of digits, indicating they represent the same number. 
     *         Otherwise, return false.
     */
    public boolean equals(LinkedNumber other) {
        // Check if bases are equal.
    	if (this.base != other.base) return false;
        // Comparing from the front
    	DLNode<Digit> thisCurrent = this.front;
        DLNode<Digit> otherCurrent = other.front;
        // Continue till there are no more digits to compare.
        while (thisCurrent != null && otherCurrent != null) {
            // If corresponding digits are not equal return false
        	if (!thisCurrent.getElement().equals(otherCurrent.getElement())) {
                return false;
            }
        	// Move to next node
            thisCurrent = thisCurrent.getNext();
            otherCurrent = otherCurrent.getNext();
        }
        // if they end at the same time, and completed above they are equal.
        return thisCurrent == null && otherCurrent == null;
    }

    /**
     * Converts the current LinkedNumber to a new base. This method first verifies the 
     * current number in its original base, then converts it to an intermediate decimal
     * representation, and finally constructs a new LinkedNumber in the specified new base.
     *
     * @param newBase The base to which the number should be converted. Must be a 
     *                positive integer greater than 1.
     * @return A new LinkedNumber instance representing the same numerical value as 
     *         this LinkedNumber, but in the specified new base.
     * @throws LinkedNumberException If the current number is not valid in its original 
     *                               base (e.g., contains digits not allowed in its base).
     */
    public LinkedNumber convert(int newBase) {
    	// Checking if current number is valid.
    	if (!isValidNumber()) {
    		throw new LinkedNumberException("cannot convert invalid number");
    	}
    	
    	// Convert the current number to decimal form.
    	int decimalValue = toDecimal();
    	    
    	// convert from decimal to new base.
    	return fromDecimal(decimalValue, newBase);
    }
    
    /**
     * Converts the number represented by this LinkedNumber from its current base to its 
     * decimal (base 10) equivalent. 
     *
     * @return The decimal equivalent of the number represented by this LinkedNumber.
     */
    private int toDecimal() {
    	int decimalValue = 0;
    	// Start from front.
    	DLNode<Digit> current = front;
    	// Get total number of digits and find the exponent for the most significant digit.
    	int numDigits = getNumDigits();
    	int exponent = numDigits - 1;
    	// Go through each node till an empty digit appears (the end).
    	while (current != null) {
    		// Convert to decimal value.
    		int digitValue = current.getElement().getValue();
    		decimalValue += digitValue * Math.pow(base, exponent);
    		// Continue to next node.
    		current = current.getNext();
    		exponent--; 
    	}
    	
    	// Return decimal value.
    	return decimalValue;
    }

    /**
     * Converts a decimal value to a LinkedNumber represented in a specified new base. 
     *
     * @param decimalValue The decimal value to be converted into the new base.
     * @param newBase The base into which the decimal value will be converted. Must be a 
     *                positive integer greater than 1.
     * @return A new LinkedNumber instance representing the decimal value in the specified 
     *         new base. If the decimal value is 0, returns a LinkedNumber representing 0 
     *         in the new base.
     */	
    private LinkedNumber fromDecimal(int decimalValue, int newBase) {
	    // Special case for 0.
    	if (decimalValue == 0) {
	        return new LinkedNumber("0", newBase); 
	    }
    	StringBuilder sb = new StringBuilder();
	    // Convert the decimal number to the new base.
    	while (decimalValue > 0) {
	        int remainder = decimalValue % newBase;
	        char digitChar = remainder >= 10 ? (char) ('A' + (remainder - 10)) : (char) ('0' + remainder);
	        sb.insert(0, digitChar); 
	        decimalValue /= newBase;
	    }
    	// Return the new LinkedNumber which is now in the desired base.
	    return new LinkedNumber(sb.toString(), newBase);
	}

    /**
     * Adds a digit to the linked list representing the number at a specified position from the rear. 
     * The position is calculated from the rear, with 0 being the immediate next position after the last digit. 
     * This method handles insertion at the beginning, middle, and end of the list, updating links between nodes as necessary.
     *
     * @param digit The digit to be added.
     * @param position The position from the rear where the digit should be added. A position of 0 
     *                 would add the digit at the end, while a position equal to the number of digits 
     *                 would place it at the beginning of the number.
     * @throws LinkedNumberException if the specified position is invalid, i.e., less than 0 or 
     *         greater than the number of digits in the number.
     */
    public void addDigit(Digit digit, int position) {
	    // Find total length of list
    	int numDigits = getNumDigits();
    	// Calculate equivalent position from the front based on position.
	    int positionFromFront = numDigits - position;

	    // Check if position is valid.
	    if (positionFromFront < 0 || positionFromFront > numDigits) {
	        throw new LinkedNumberException("invalid position");
	    }
	    
	    // Create new node for the digit.
	    DLNode<Digit> newNode = new DLNode<>(digit);
	    // Insert from the front of the list.
	    if (positionFromFront == 0) {
	        if (front != null) {
	            newNode.setNext(front);
	            front.setPrev(newNode);
	        }
	        front = newNode;
	        if (rear == null) { 
	            rear = newNode;
	        }
	    // Insert from the rear of the list.
	    } else if (positionFromFront == numDigits) {
	        rear.setNext(newNode);
	        newNode.setPrev(rear);
	        rear = newNode;
	    } else { 
	        DLNode<Digit> current = front;
	        // Go to node after which the new node should be inserted.
	        for (int i = 1; i < positionFromFront; i++) {
	            current = current.getNext();
	        }
	        // Insert the new node in correct position.
	        newNode.setNext(current.getNext());
	        newNode.setPrev(current);
	        if (current.getNext() != null) { 
	            current.getNext().setPrev(newNode);
	        }
	        current.setNext(newNode);
	    }
	}

    /**
     * Removes a digit from the linked list representing the number at a specified position 
     * from the rear and returns the decimal value represented by the removed digit in the 
     * context of the entire number.
     *
     * @param position The position from the rear of the digit to be removed, where 0 is the 
     *                 least significant digit.
     * @return The decimal value of the removed digit, calculated based on its position and 
     *         the base of the number.
     * @throws LinkedNumberException if the specified position is invalid, i.e., less than 0 
     *         or greater than or equal to the number of digits in the number.
     */
	public int removeDigit(int position) {
	    // Find length of list.
		int numDigits = getNumDigits();
	    // Check if it is valid.
		if (position < 0 || position >= numDigits) {
	        throw new LinkedNumberException("invalid position");
	    }
		
		// Traverse to the rear and to the specified position.
	    DLNode<Digit> current = rear;
	    for (int i = 0; i < position; i++) {
	        current = current.getPrev();
	    }
	    
	    // Calculate the decimal value of removed digit.
	    int digitValue = current.getElement().getValue();
	    int decimalValue = (int) (digitValue * Math.pow(base, position));
	    
	    // Remove digit from the list.
	    if (current == front) {
	        front = current.getNext();
	        if (front != null) {
	            front.setPrev(null);
	        }
	        if (front == null) { 
	            rear = null;
	        }
	    } else if (current == rear) {
	        rear = current.getPrev();
	        if (rear != null) {
	            rear.setNext(null);
	        }
	    } else {
	        current.getPrev().setNext(current.getNext());
	        if (current.getNext() != null) {
	            current.getNext().setPrev(current.getPrev());
	        }
	    }
	    // Return the calculated decimal value of the removed digit.
	    return decimalValue;
	}
}
