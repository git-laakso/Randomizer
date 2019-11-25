# Randomizer
Android studio application that generates random six digit authentication code from the button and reads user input in two fields. Programs sends generated code to the email address using JavaMail API ( Gmail authentication).

![Proto](https://user-images.githubusercontent.com/52996898/69582462-5537cd00-0fe1-11ea-8a5c-bd86983884c4.PNG)

# Listing steps
 * The program asks user to fill email address on specific fields and confirm them. If the following fields do not match toast appears and asks user to check spelling.

 * At this point we should click button "Generate code" to generate secret 6 digit code (numbers and letters including) that appears on password field below "Generate code" button.

 * If the email input fields match user can proceed to the next page by clicking "Next" button.
 * Program shows animated circle that sends message and also toasts user input.
 * Example "Sending authentication code to: MyEmailAddress2019@gmail.com".

 * User lands on the last page that shows text field: "Email and notification has been sent".
 * There will be also notification that tells code has been sent from the program.
<br>

## Build
Project was build on API 29.
## UML
The following UML diagram shows the main idea. 
However this diagram does not show button that generates code and it does not show sending email screen logics and values from the landing page.
<br>
![uml-1](https://user-images.githubusercontent.com/52996898/69579366-0129ea00-0fdb-11ea-997c-9133ee48a8a9.PNG)
