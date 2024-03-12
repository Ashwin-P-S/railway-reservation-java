import java.util.*;

public class Main
{
    public static void main(String[] arg)
    {
        boolean loop = true;
        int age, choice;
        String name, preferredBerth;

        Scanner in = new Scanner(System.in);
        TicketBooker booker = new TicketBooker();

        while(loop)
        {
            System.out.println("1. Booking");
            System.out.println("2. Cancellation");
            System.out.println("3. Booked Tickets");
            System.out.println("4. Available Tickets");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            choice = in.nextInt();

            switch(choice)
            {
                case 1:
                {   
                    if (booker.availableSeats() == 0)
                    {
                        System.out.println("---------------------");
                        System.out.println("No Seats Available");
                        System.out.println("---------------------");
                        break;
                    }

                    System.out.println("Enter Name, Age and Preffered Berth (L, M, U):");
                    name = in.next();
                    age = in.nextInt();
                    preferredBerth = in.next();

                    Passenger p = new Passenger(name, age, preferredBerth);
                    booker.bookTicket(p);
                    break;   
                }

                case 2:
                {
                    if (!booker.bookedTicketList.isEmpty())
                    {
                        System.out.println("Enter Passenger ID: ");
                        int id = in.nextInt();
                        booker.cancelTicket(id);
                    }
                    else
                    {
                        System.out.println("-----------------------------------");
                        System.out.println("No Tickets were booked!");
                        System.out.println("-----------------------------------");   
                    }
                    break;   
                }

                case 3:
                {
                    booker.showBookedTickets();
                    break;   
                }

                case 4:
                {
                    booker.showAvailableTickets();
                    break;   
                }

                case 5:
                {
                    loop = false;
                    break;
                }

                default:
                    System.out.println("Invalid Choice... Please try again!");
                    
            }
            
        }
    }
}