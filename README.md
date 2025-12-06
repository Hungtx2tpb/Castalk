**Castalk Mobile Automation – Quick Guide**

**1. Where to view the test cases**

All test cases are written in Gherkin (.feature) files located at:

src/test/resources/features.Castalk/

Examples:

src/test/resources/features.Castalk/Login_IOS.feature
src/test/resources/features.Castalk/Chat_IOS.feature

Each scenario has a tag, for example:

@LG_01
Scenario: Login with valid credentials

**2. How to run a specific test case**

Open the .feature file and copy the tag of the scenario you want to run
(e.g., @LG_01, @Chat_02, etc.)

Open the test runner:

src/test/java/bb/TestSuite.java

Paste the tag into:
tags = "@LG_01"

Example – running a Chat test:
tags = "@Chat_02"

Run the test suite:

Right-click → Run TestSuite

Or via Maven:

mvn clean verify

**3. How to change the iOS device UDID**

Open the Appium configuration file:

src/test/resources/serenity.conf

Find this section:

udid = "00008130-001014580AE8001C"
platformVersion = "18.2"
bundleId = "com.castalk.app"

Replace the UDID with your device’s UDID
(You can get it from Xcode → Devices and Simulators)

Example:
udid = "YOUR_DEVICE_UDID"


**Quick summary for new users**

View test cases:
src/test/resources/features.Castalk/*.feature

Run any test:
Copy tag → paste into tags = "..." inside
src/test/java/bb/TestSuite.java

Change device:
Update UDID inside
src/test/resources/serenity.conf
