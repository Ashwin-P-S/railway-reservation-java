import java.util.*;

class TicketBooker
{
    int MAX_WL = 10;
    int MAX_RAC = 10;

    static int availableLowerBerth = 10;
    static int availableMiddleBerth = 10;
    static int availableUpperBerth = 10;
    static int availableRACs = 10;
    static int availableWaitingList = 10;

    static List<Integer> lowerBerthPosition = new ArrayList<>();
    static List<Integer> middleBerthPosition = new ArrayList<>();
    static List<Integer> upperBerthPosition = new ArrayList<>();
    static List<Integer> racPosition = new LinkedList<>();
    static List<Integer> waitingListPosition = new LinkedList<>();

    static List<Integer> bookedTicketList = new ArrayList<>();
    static Queue<Integer> racList = new LinkedList<>();
    static Queue<Integer> waitingList = new LinkedList<>();

    static Map<Integer, Passenger> details = new HashMap<>();

    TicketBooker()
    {
        for(int i=1; i<=availableLowerBerth; i++)
            lowerBerthPosition.add(i);

        for(int i=1; i<=availableMiddleBerth; i++)
            middleBerthPosition.add(i);

        for(int i=1; i<=availableUpperBerth; i++)
            upperBerthPosition.add(i);

        for(int i=1; i<=availableRACs; i++)
            racPosition.add(i);

        for(int i=1; i<=availableWaitingList; i++)
            waitingListPosition.add(i);
    }

    private void updateDetails(Passenger p)
    {
        bookedTicketList.add(p.passengerId);
        details.put(p.passengerId, p);
    }

    private void bookLowerBerth(Passenger p)
    {
        p.alloted = lowerBerthPosition.remove(0) + "L";
        
        availableLowerBerth--;
        updateDetails(p);
        System.out.println("Lower Berth Given");
    }

    private void bookMiddleBerth(Passenger p)
    {
        p.alloted = middleBerthPosition.remove(0) + "M";
        
        availableMiddleBerth--;
        updateDetails(p);
        System.out.println("Middle Berth Given");
    }

    private void bookUpperBerth(Passenger p)
    {
        p.alloted = upperBerthPosition.remove(0) + "U";

        availableUpperBerth--;
        updateDetails(p);
        System.out.println("Upper Berth Given");
    }
    
    void bookTicket(Passenger p)
    {
        if (availableLowerBerth == 0 && availableMiddleBerth == 0 && availableUpperBerth == 0)
        {
            System.out.println("Preferred Berth Not Available!");
            if (availableRACs != 0)
            {
                p.alloted = racPosition.remove(0) + "RAC";

                availableRACs--;
                updateDetails(p);
                racList.add(p.passengerId);
                System.out.println("Booked in RAC");
            }
            else
            {
                p.alloted = waitingListPosition.remove(0) + "WL";
                
                availableWaitingList--;
                updateDetails(p);
                waitingList.add(p.passengerId);
                System.out.println("Booked in Waiting List");
            }
        }
        else
        {
            if (p.preferredBerth.equals("L") && availableLowerBerth > 0)
            {
                System.out.println("Preferred Berth Available!");
                bookLowerBerth(p);
            }
            else if (p.preferredBerth.equals("M") && availableMiddleBerth > 0)
            {
                System.out.println("Preferred Berth Available!");
                bookMiddleBerth(p);
            }
            else if (p.preferredBerth.equals("U") && availableUpperBerth > 0)
            {
                System.out.println("Preferred Berth Available!");
                bookUpperBerth(p);
            }
            else
            {
                System.out.println("Preferred Berth Not Available!");
                if (availableLowerBerth != 0)
                    bookLowerBerth(p);

                else if (availableMiddleBerth != 0)
                    bookMiddleBerth(p);
                    
                else if (availableUpperBerth != 0)
                    bookUpperBerth(p);
            }
        }
    }

    void cancelTicket(int id)
    {
        Passenger p1 = details.get(id);
        Passenger p2 = null, p3 = null;
        int seatNo;
        String alloted;

        if (p1 == null)
        {
            System.out.println("No Tickets were booked with ID: " + id);
            return;
        }

        System.out.println("------------------------------------");
        System.out.println("Successfully Cancelled Ticket");
        System.out.println("------------------------------------");
        System.out.println("Name: " + p1.name);
        System.out.println("Age: " + p1.age);
        System.out.println("------------------------------------");

        if (availableRACs < MAX_RAC)
        {
            p2 = details.get(racList.peek());

            if (availableWaitingList < MAX_WL)
            {
                p3 = details.get(waitingList.peek());
                seatNo = Integer.parseInt(p3.alloted.substring(0, 1));

                availableWaitingList++;

                alloted = p2.alloted;
                p2.alloted = p1.alloted;
                p3.alloted = alloted;

                racList.add(p3.passengerId);
                waitingList.remove();
                waitingListPosition.add(0, seatNo);

                bookedTicketList.remove(bookedTicketList.indexOf(p1.passengerId));
                details.remove(p1.passengerId);
                details.put(p2.passengerId, p2);
                details.put(p3.passengerId, p3);
                p1 = null;
            }
            else
            {
                availableRACs++;
                seatNo = Integer.parseInt(p2.alloted.substring(0, 1));
                racList.remove();
                racPosition.add(0, seatNo);

                p2.alloted = p1.alloted;
                bookedTicketList.remove(bookedTicketList.indexOf(p1.passengerId));
                details.remove(p1.passengerId);
                details.put(p2.passengerId, p2);
                p1 = null;
            }
        } 
        else
        {
            seatNo = Integer.parseInt(p1.alloted.substring(0, 1));
            alloted = p1.alloted.substring(1);

            if (alloted.equals("L"))
            {
                availableLowerBerth++;
                lowerBerthPosition.add(0, seatNo);
            }

            else if (alloted.equals("M"))
            {
                availableMiddleBerth++;
                middleBerthPosition.add(0, seatNo);
            }

            else if (alloted.equals("U"))
            {
                availableUpperBerth++;
                upperBerthPosition.add(0, seatNo);
            }

            details.remove(p1.passengerId);
            bookedTicketList.remove(bookedTicketList.indexOf(p1.passengerId));
            p1 = null;
        }
    }

    void showBookedTickets()
    {
        if (bookedTicketList.isEmpty())
        {
            System.out.println("-----------------------------------");
            System.out.println("No Tickets were booked!");
            System.out.println("-----------------------------------");
        }

        for(Passenger p: details.values())
        {
            System.out.println("-----------------------------------");
            System.out.println("Passenger ID: " + p.passengerId);
            System.out.println("Name: " + p.name);
            System.out.println("Age: " + p.age);
            System.out.println("Allocated Seat: " + p.alloted);
            System.out.println("Preferred Berth: " + p.preferredBerth);
            System.out.println("-----------------------------------");
        }
    }

    void showAvailableTickets()
    {
        if (availableSeats() == 0)
        {
            System.out.println("-----------------------------------");
            System.out.println("No Seats Available!");
            System.out.println("-----------------------------------");
            return;
        }

        System.out.println("Lower Berths Available : " + availableLowerBerth);
        System.out.println("Middle Berths Available : " + availableMiddleBerth);
        System.out.println("Upper Berths Available : " + availableUpperBerth);
        System.out.println("RACs Available : " + availableRACs);
        System.out.println("Waiting List Available : " + availableWaitingList);
    }

    int availableSeats()
    {
        int seats = this.availableLowerBerth;
        seats += this.availableMiddleBerth;
        seats += this.availableUpperBerth;
        seats += this.availableRACs;
        seats += this.availableWaitingList;

        return seats;
    }
    
}