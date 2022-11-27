package dev.pb.pb_backend.projection;

import java.util.Date;

public interface ItemProjection {
	
	int getItemCode();
	String getItemName();
	String getItemImage();
	String getEtcDetails();
	Date getHarvestStart();
	Date getHarvestEnd();
	
}
