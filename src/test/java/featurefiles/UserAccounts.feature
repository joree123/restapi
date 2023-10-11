
Feature: Validating users
 
Background: 

  @Login @Regression
  Scenario: verify if user can successfully log in using valid credentials
    Given Login payload with username and password
    When User call LoginApi with Post http request
    Then the api call got success with status code 201  
    

  @Regression @Adduser
  Scenario Outline: verify if user can be successfully added using AddUserApi
    Given Add user Payload with <accountno.> <departmentno.> <salary> <pincode>
    When User call AddUserApi with Post http request
    Then the api call got success with status code 201
    And the success message is displayed
    

    Examples: 
      | accountno. | departmentno. | salary  |pincode |
      | "TA-3425231" |     "5" | "40000" |"345623"|
      
      
     @Getuser @Regression
    Scenario:verify if the user informations can be seen using GetUserApi
    When User call GetUserApi with Get http request
    Then the api call got success with status code 200
     And The users info is displayed 
      
     @Updateuser @Regression
    Scenario Outline:verify if the user information can be updated using UpdateUserApi
    Given Update user payload with <accountno.> <departmentno.> <salary> <pincode>
    When User call UpdateUserApi with Put http request
    Then the api call got success with status code 200
     And the success message is displayed
      
       Examples: 
      | accountno. | departmentno. | salary  |pincode |
      | "TA-3425231" |     "6" | "40000" |"345623"|
      
      
    @Deleteuser @Regression
    Scenario:verify if the user is deleted using DeleteUserApi
    Given Delete user payload
    When User call DeleteUserApi with Delete http request
    Then the api call got success with status code 200
     And the success message is displayed
       
    
    
    
    
    
    
      
