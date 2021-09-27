/*
Autor: Sergio Alejandro Sánchez Juárez
Fecha: 27 de septiembre de 2021
*/
package agent;

import jade.core.Agent;
import behaviours.RequestPerformer;
import gui.BookBuyerGui;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {
    private String bookTitle;
    private AID[] sellerAgents;
    private int ticker_timer = 10000;
    private BookBuyerAgent this_agent = this;
    
    private BookBuyerGui gui;
  
    protected void setup() {
        System.out.println("El agente comprador " + getAID().getName() + " esta listo.");
        
        gui = new BookBuyerGui(this);
        gui.showGui();
    }
    
    public void search(String title){
        if(title.length()>0) {
            bookTitle = title;
            gui.showMessage("Libro a comprar: " + bookTitle);
            System.out.println("Libro a comprar: " + bookTitle);
      
            addBehaviour(new TickerBehaviour(this, ticker_timer) {
                protected void onTick() {
                    gui.showMessage("Intentando comprar el libro: " + bookTitle);
                    System.out.println("Intentando comprar el libro: " + bookTitle);
          
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("book-selling");
                    template.addServices(sd);
          
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        
                        gui.showMessage("\nEncontrados los siguientes agentes vendedores:");
                        
                        sellerAgents = new AID[result.length];
                        for(int i = 0; i < result.length; i++) {
                            sellerAgents[i] = result[i].getName();
                            gui.showMessage(sellerAgents[i].getName());
                        }
            
                    }catch(FIPAException fe) {
                        fe.printStackTrace();
                    }
          
                    myAgent.addBehaviour(new RequestPerformer(this_agent,gui));
                }
            });
        } else {
            gui.showMessage("\nNo se ha especificado un titulo de libro a buscar");
            System.out.println("\nNo se ha especificado un titulo de libro a buscar");
            doDelete();
        }
    }
  
    protected void takeDown() {
        gui.showMessage("Agente comprador " + getAID().getName() + " ha terminado.\n\n**Por favor cierre la interfaz.**");
        System.out.println("Agente comprador " + getAID().getName() + " ha terminado.");
        //gui.dispose();
    }
  
    public AID[] getSellerAgents() {
        return sellerAgents;
    }
  
    public String getBookTitle() {
        return bookTitle;
    }
}
