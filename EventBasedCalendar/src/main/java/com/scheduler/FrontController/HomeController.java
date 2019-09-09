package com.scheduler.FrontController;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.daos.ClientDao;
import com.daos.EventDao;
import com.entities.Client;
import com.entities.Events;




@RestController
public class HomeController {
	@Autowired
	ClientDao clientDao;
	
	@Autowired
	EventDao eventDao;
	
	@RequestMapping(value="/" , method=RequestMethod.GET)
	public ModelAndView Print(ModelMap map){
		System.out.println("I m in Preform");
		
		ModelAndView mv=new ModelAndView("HomePage");
		Client cObj=new Client();
		mv.addObject("cObj",cObj);   
		return mv;
	}
	
	
	@RequestMapping(value="/submitOnDb" , method=RequestMethod.POST)
	public ResponseEntity<String> submitClient(@RequestBody Client cObj)
	{
		System.out.println("I m getting called");
		System.out.println("client Obj : "+cObj);
		
				
		/*try{
		*/	Client clientNew=null;
			//if(msg!="")
			int flag=0;
			List<Client>  clientList=clientDao.getAllClient();
//			for(Client c:clientList)
//			{
//				if((c.getClientName().equals(cObj.getClientName()))/*&&(c.getArrivalDate().equals(cObj.getArrivalDate()))&&(c.getDeptDate().equals(cObj.getDeptDate()))&&(c.getAgenda().equals(cObj.getAgenda()))&&(c.getAccount().equals(cObj.getAccount()))*/)
//				{	
//						int cIdPrevious=c.getClientId();
//						clientNew=clientDao.getClientById(cIdPrevious);
//						flag=1;
//						break;
//				
//				}
//				
//			}
			
			//if(flag==0)
		 clientDao.addClient(cObj);
			
			 
			
		/*String eventData=cObj.getEventData();
		System.out.println(eventData);*/
		 for(Events event:cObj.getEventData()){
			 event.setCli(cObj);
			 eventDao.addEvent(event);
		 }
		 
		 
		return new ResponseEntity<String>("Done", HttpStatus.OK);	
	}
		
//		}catch(Exception e){
//			e.printStackTrace();
////			mv=new ModelAndView("HomePage");
////			clientDao.delClient(cObj.getClientId());
////			mv.addObject("msg","There is some problem..Please fill the Event Details");
//		//	return mv;
//		}
//		mv=new ModelAndView("HomePage");
//		mv.addObject("msg","Client Itinerary Successfully");
//		return mv;
		
	
	
	@RequestMapping(value="/view" , method=RequestMethod.GET , headers="Accept=application/json")
	public  List<Client> viewClient(ModelMap map){
			System.out.println("I m here in view Client method ");
			List<Client>  clientList=clientDao.getAllClient();
			for(Client client:clientList)
			{
				Date arrvdt=client.getArrivalDate();
				Date deptdt=client.getDeptDate();
				
				/*DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
				String strDate1=dateformat.format(arrvdt);
				String strDate2=dateformat.format(deptdt);
				
				client.setStringarrive(strDate1);
				client.setStringdepart(strDate2);*/
				
				System.out.println("arrival Date : "+arrvdt);
				System.out.println("Departure Date : "+deptdt);
			
				
			}
			map.addAttribute("clients",clientList);
			return clientList;
		
	}
	
/*	@RequestMapping(value="/viewClientEvent2/{clientId}" , method=RequestMethod.GET)
	public ModelAndView viewClientEventss(@PathVariable int clientId,ModelMap map){
			System.out.println(clientId);
			
			
			Client c=clientDao.viewClient(clientId);
			
			
			ModelAndView mv=new ModelAndView("EventView"); 
			mv.addObject("clientName",c.getClientName());  
			mv.addObject("clientId",c.getClientId()); 
			List<Events> eventList=eventDao.viewAllEventsById(clientId);
			for(Events event:eventList){
			System.out.println("Event"+event);
			}
			mv.addObject("EventsList", eventList);
			
			return mv;
		
	}*/

@RequestMapping(value="/deleteEvent/{eventId}/{clientId}",method=RequestMethod.GET)
	public ModelAndView deleteEvent(@PathVariable int eventId,ModelMap map,@PathVariable int clientId)
	{
		eventDao.deleteEvent(eventId);
		//map.addAttribute("msg", "Event deleted");
		Client c=clientDao.viewClient(clientId);
		ModelAndView mv=new ModelAndView("EventView");
		mv.addObject("clientName",c.getClientName());
		List<Events> eventList=eventDao.viewAllEventsById(clientId);
		mv.addObject("EventsList", eventList);
		
		return mv;
	}

	@Autowired
	HttpSession session;

	@RequestMapping(value="/viewClientEvent/{clientId}" , method=RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody List<Events> viewClientEvent(@PathVariable int clientId){
		System.out.println("Hello 1");
		session.setAttribute("clientId",clientId);
		List<Events> eventList=eventDao.viewAllEventsById(clientId);
		return eventList;
	}
	
	@RequestMapping(value="/viewAllEvents" , method=RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody List<Events> viewAllEvents(){
		
		int clientId=(Integer)session.getAttribute("clientId");
		//int clientId=63;
		List<Events> eventList=eventDao.viewAllEventsById(clientId);
//		for(Events events:eventList){
//			System.out.println(events.getTitle());
//		}
		return eventList;
	}
	
}
