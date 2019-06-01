/*
  Web client

 This sketch connects to a website (http://www.google.com)
 using an Arduino Wiznet Ethernet shield.

 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13

 created 18 Dec 2009
 by David A. Mellis
 modified 9 Apr 2012
 by Tom Igoe, based on work by Adrian McEwen

 */

#include <SPI.h>
#include <Ethernet.h>
#include <time.h>


static const String IP = "192.168.1.69";
static const int PORT = 8080;


// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
IPAddress server(192,168,1,69);   //<---------- IP BUT WITH ,



//config controllers name
static const char tempController[] = "temperature";
static const char processPercentageController[] = "processPercentage";


// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };



// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 0, 177);
IPAddress myDns(192, 168, 0, 1);

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;

// Variables to measure the speed
unsigned long beginMicros, endMicros;
unsigned long byteCount = 0;
bool printWebData = true;  // set to false for better speed measurement

void setup() {
  
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // start the Ethernet connection:
  Serial.println("Initialize Ethernet with DHCP:");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // Check for Ethernet hardware present
    if (Ethernet.hardwareStatus() == EthernetNoHardware) {
      Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
      while (true) {
        delay(1); // do nothing, no point running without Ethernet hardware
      }
    }
    if (Ethernet.linkStatus() == LinkOFF) {
      Serial.println("Ethernet cable is not connected.");
    }
    // try to congifure using IP address instead of DHCP:
    Ethernet.begin(mac, ip, myDns);
  } else {
    Serial.print("  DHCP assigned IP ");
    Serial.println(Ethernet.localIP());
  }
  // give the Ethernet shield a second to initialize:
  delay(1000);
  Serial.print("connecting to ");
  Serial.print(server);
  Serial.println("...");


    
    
  // if you get a connection, report back via serial:
  if (client.connect(server, PORT)) {
    Serial.print("connected to ");
    Serial.println(client.remoteIP());
    // Make a HTTP request:

    
    //sendTemp();
    
    //client.println("GET http://192.168.1.69:8080/greeting HTTP/1.1");
    //client.println("Host: 192.168.1.69");
    //client.println("Connection: close");
    //client.println();

      
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
  beginMicros = micros();
}


void sendTemp(float temp)
{
  String request = "GET http://" + IP + ":" + PORT + "/" + tempController + "/settemp?temp=" + String(temp)+" HTTP/1.1";

  //Serial.println(request);
  
  client.println(request);
  
  String host = "Host: " + IP;
  client.println(host);
  //client.println("Connection: close");
  client.println();
}


void printState()
{
  Serial.println("-----------HTTP request---------");
  
  int len = client.available();
  if (len > 0) {
    byte buffer[80];
    if (len > 80) len = 80;
    client.read(buffer, len);
    if (printWebData) {
      Serial.write(buffer, len); // show in the serial monitor (slows some boards)
    }
    byteCount = byteCount + len;
  }

  Serial.println("\n-----------//---------");
}

void loop() {
  // if there are incoming bytes available
  // from the server, read them and print them:
  
  
  printState();
  
  delay(10000);

  int max_number = 40, minimum_number = 1;
  float randTemp = rand() % (max_number + 1 - minimum_number) + minimum_number;
  sendTemp(randTemp);
    
  
}
