package rummydemo;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.shephertz.app42.server.idomain.BaseServerAdaptor;
import com.shephertz.app42.server.idomain.IZone;

/**
 *
 * @author shephertz
 */
public class RummyServerExtension extends BaseServerAdaptor{
    
    @Override
    public void onZoneCreated(IZone zone)
    {             
        zone.setAdaptor(new RummyZoneExtension(zone));
        System.out.println("Server started");
    }
    
}
