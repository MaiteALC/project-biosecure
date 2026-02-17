> **Context:**

BioSecure’s domain involves complex entities that require numerous parameters for instantiation. To ensure data integrity, the system performs multiple validations on these parameters. If an exception is thrown every time a single validation rule fails, the application flow is immediately interrupted. This forces the user to correct errors one by one.

For instance, if an entity requires five parameters and all of them are invalid, the normal execution flow would be interrupted five consecutive times. This results in a poor user experience and prevents the application from providing a comprehensive overview of all invalid data at once. Therefore, we need a more dynamic and user-friendly approach to validation.

> **Decision:**

We have decided to implement the Notification Pattern. This pattern involves creating a "notification" object that gathers all validation failures and their respective details during the instantiation process. Instead of throwing an exception at the first sign of an error, the system executes all validation rules and stores any issues in this object.

Once all rules have been processed, the application checks the notification object. If errors are present, a single, detailed exception is thrown, containing all collected validation messages. Otherwise, the execution continues normally. The notification system utilizes a base `ValidationException` class, which includes formatting logic for clear error reporting. Other domain-specific exceptions will extend this class to reuse this logic and improve semantic clarity.

> **Consequences:**

- **Positive:** Users receive a detailed summary of all failed validations simultaneously. 
- **Positive:** Corrections can be addressed in a single pass rather than through multiple, repetitive cycles of "submit and fail."
- **Negative:** Existing exception classes required refactoring to extend `ValidationException` and integrate with the new notification logic.
