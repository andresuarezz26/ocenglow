          //Initializate pins numbers,dalay, intto receive data and a counter   
                #include <SoftwareSerial.h>  
                int columnas[] = {5,6,9,10,11};
                int d = 80;
                int counter = 0;
                int incomingByte;
         
                void setup()
                {  
                  Serial.begin(9600);  // Begin the serial monitor at 9600bps            
                  delay(100);  // Short delay, wait for the Mate to send back CMD
         
                  
            
                  for(int i = 0; i < 5; i++){
                    pinMode(columnas[i], OUTPUT);
                  }
                  
                  /*pinMode(I, INPUT);
                  pinMode(D, INPUT);*/
                }
                void loop()
                {  if(Serial.available()>0)  // If the bluetooth sent any characters
                  {
      
                   incomingByte = Serial.read();
                   switch(incomingByte){
                   case '1':
                   {
                     Serial.println("Mover Izquierda");
                     //Mover izquierda
                     moverIzquierda();
                     
                   }
                   break;
                   
                   case '2':
                   {
                     Serial.println("Mover Derecha");
                     //Mover derecha
                     moverDerecha();
                   }
                   break;
                   
                   case '3':
                   d=110;
                   break;
                   
                   case '4':
                   d=90;
                   break;
                   
                    case '5':
                   d=70;
                   break;
                   
                    case '6':
                    Serial.println("6");
                   d=50;
                   break;
                   
                   case '7':
                   d=30;
                   Serial.println("7");
                   break;
                   
                   default:
                   {
                    Serial.println("Prende Todo");
                     
                   }
                   }
                   
                  }else{
                    //Si el bluetooth no esta disponible
                    prenderTodo();
                  }
                 
                }
              
                
                void moverIzquierda(){
                  //Turn On 5 column
                  digitalWrite(columnas[4], HIGH);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Turn On 4 column
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], HIGH);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Turn On 3 column
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], HIGH);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Prender columna 2
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], HIGH);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Turn On 1 column
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], HIGH);
                  delay(d);
                
                }
                
                void moverDerecha(){
                  //Prender columna 1
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], HIGH);
                  delay(d);
                  
                  //Turn On 2 column
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], HIGH);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Prender columna 3
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], LOW);
                  delay(d);
                  digitalWrite(columnas[2], HIGH);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Turn On 4 column
                  digitalWrite(columnas[4], LOW);
                  delay(d);
                  digitalWrite(columnas[3], HIGH);
                  delay(d);
                  digitalWrite(columnas[2], LOW);
                  delay(d);
                  digitalWrite(columnas[1], LOW);
                  delay(d);
                  digitalWrite(columnas[0], LOW);
                  delay(d);
                  
                  //Turn On 5 column
                  digitalWrite(columnas[4], HIGH);
                  digitalWrite(columnas[3], LOW);
                  digitalWrite(columnas[2], LOW);
                  digitalWrite(columnas[1], LOW);
                  digitalWrite(columnas[0], LOW);
                }
                
                void prenderTodo(){
                  digitalWrite(columnas[4], HIGH);
                  delay(d);
                  digitalWrite(columnas[3], HIGH);
                  delay(d);
                  digitalWrite(columnas[2], HIGH);
                  delay(d);
                  digitalWrite(columnas[1], HIGH);
                  delay(d);
                  digitalWrite(columnas[0], HIGH);
                  delay(d);
                }

