package LinkedNumbers;

/**
 * A custom exception class for handling errors specific to operations within the 
 * LinkedNumber class. This exception extends RuntimeException, making it
 * an unchecked exception. It is used to signal issues such as invalid 
 * operations or arguments when working with LinkedNumber instances, 
 * like attempting to convert or manipulate numbers in ways that are not supported 
 * by their current state or base.
 */
public class LinkedNumberException extends RuntimeException {
    
	/**
     * A unique identifier for this exception class to support serialization.
     */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs a new LinkedNumberException with the specified detail message.
     * The message can be used to provide more information about the exception.
     * 
     * @param msg The detail message. The detail message is saved for later retrieval 
     *            by the Throwable#getMessage()} method.
     */
	public LinkedNumberException(String msg) {
        // Call parent class with the message.
		super(msg);
    }
}