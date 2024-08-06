# SafetyNet-Alerts
SafetyNet Alerts application with deliverables defined by [OpenClassrooms Back-end Programming with Java &amp; Spring](https://openclassrooms.com/en/paths/248-back-end-programming-with-java-spring)

## URLs
http://localhost:8080/firestation?stationNumber=<station_number>
This URL should return a list of people serviced by the corresponding fire station. So if station number = 1,
it should return the people serviced by station number 1. The list of people should include these specific
pieces of information: first name, last name, address, phone number. As well, it should provide a
summary of the number of adults in the service area and the number of children (anyone aged 18 or
younger).

http://localhost:8080/childAlert?address=<address>
This URL should return a list of children (anyone under the age of 18) at that address. The list should
include the first and last name of each child, the child’s age, and a list of other persons living at that
address. If there are no children at the address, then this URL can return an empty string.

http://localhost:8080/phoneAlert?firestation=<firestation_number>
This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.We’ll
use this to send emergency text messages to specific households.

http://localhost:8080/fire?address=<address>
This URL should return the fire station number that services the provided address as well as a list of all of
the people living at the address. This list should include each person’s name, phone number, age,
medications with dosage, and allergies.

http://localhost:8080/flood/stations?stations=<a list of station_numbers>
This should return a list of all the households in each fire station’s jurisdiction. This list needs to group
people by household address, include name, phone number, and age of each person, and any
medications (with dosages) and allergies beside each person’s name.

http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
This should return the person’s name, address, age, email, list of medications with dosages and allergies.
If there is more than one person with the same name, this URL should return all of them.

http://localhost:8080/communityEmail?city=<city>
This will return the email addresses of all of the people in the city.
