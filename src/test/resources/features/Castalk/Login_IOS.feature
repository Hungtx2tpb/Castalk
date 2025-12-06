@login @TestRound2
Feature: Login on CasTalk

  Background:
    Given User is on Home Page

  @LG_01
  Scenario Outline: Login with valid credentials
    When User click on button More
    Then Verify User Profile page should be displayed
    When User click on Sign In
    And User signs in with Email with email "<Valid Email>" and password "<Valid Password>"
    And User click on button More
    And User click on My Account
    Then Verify user login successfully with email "<Valid Email>"

    Examples:
      | Valid Email            | Valid Password |
      | tester+126@castalk.com | 123@123Ab      |

  @LG_02
  Scenario Outline: Login with invalid password
    When User click on button More
    Then Verify User Profile page should be displayed
    When User click on Sign In
    And User signs in with Email with email "<Valid Email>" and password "<InValid Password>"
    Then Verify error message password "Please enter a valid password" is displayed

    Examples:
      | Valid Email            | InValid Password |
      | tester+126@castalk.com | 123@             |


  @LG_03
  Scenario Outline: Login with invalid email format
    When User click on button More
    Then Verify User Profile page should be displayed
    When User click on Sign In
    And User signs in with Email with email "<InValid Email>" and password "<Valid Password>"
    Then Verify error message email "Incorrect email address format. Please try again." is displayed

    Examples:
      | InValid Email | Valid Password |
      | abc@xyz       | 123@123Ab      |


  @LG_04
  Scenario: Attempt login with empty fields
    When User click on button More
    Then Verify User Profile page should be displayed
    When User click on Sign In
    And User signs in with Email with email " " and password " "
    Then Verify Sign In button should be disable

  @LG_05
  Scenario Outline: Toggle password visibility
    When User click on button More
    Then Verify User Profile page should be displayed
    When User click on Sign In
    And User signs in with Email with email " " and password "<Valid Password>"
    And User click on icon show password
    Then Verify Password should be displayed "<Valid Password>"

    Examples:
      | Valid Password |
      | 123@123Ab      |

