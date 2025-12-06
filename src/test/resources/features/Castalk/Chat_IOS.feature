@Chat @TestRound2
Feature: Chat feature on CasTalk

  Background:
    Given User is on Home Page
    When user perform Sign In with email "tester+126@castalk.com" and password "123@123Ab" if Guest

  @Chat_01
  Scenario: Open chat screen after login
    When Get AI virtual assistant name
    And User click on button Chat
    Then Verify Chat screen should be displayed
    And Verify AI virtual assistant name is displayed correct
    And Verify history Message should be displayed
    And Verify Input Message should be empty
    Then Verify Send button should be InVisible

  @Chat_02
  Scenario: Send a message receive AI response
    When Get AI virtual assistant name
    And User click on button Chat
    Then Verify Chat screen should be displayed
    When User input message "Hello, My name is Hung"
    And User click on Send button
    Then Verify response message by AI should be correct
      | value             |
      | Hi Hung!          |
      | Nice to meet you  |
      | Nice to meet you, |
      | Alright, Hung     |
      | Hello, Hung!      |
      | Hung!             |


  @Chat_03
  Scenario: Whitespace-only message validation
    When Get AI virtual assistant name
    And User click on button Chat
    Then Verify Chat screen should be displayed
    When User input message "     "
    And Check Message before send white space
    And User click on Send button
    Then Verify Message should not be send
