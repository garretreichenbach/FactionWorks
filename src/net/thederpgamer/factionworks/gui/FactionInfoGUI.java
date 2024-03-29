package net.thederpgamer.factionworks.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import org.schema.game.client.controller.manager.ingame.faction.FactionControlManager;
import org.schema.game.client.data.GameClientState;
import org.schema.game.common.controller.PlayerFactionController;
import org.schema.game.common.data.player.faction.FactionRelation.RType;
import org.schema.schine.graphicsengine.core.Controller;
import org.schema.schine.graphicsengine.core.GlUtil;
import org.schema.schine.graphicsengine.forms.gui.GUIElement;
import org.schema.schine.graphicsengine.forms.gui.GUIOverlay;
import org.schema.schine.graphicsengine.forms.gui.GUITextOverlay;
import org.schema.schine.input.InputState;
import net.thederpgamer.factionworks.faction.FactionInfo;
import net.thederpgamer.factionworks.faction.Government;
import net.thederpgamer.factionworks.faction.Organization;
import net.thederpgamer.factionworks.main.Reflectors;

@SuppressWarnings("deprecation")
public class FactionInfoGUI extends GUIElement implements Observer{
		
	public FactionInfoGUI(InputState var1) {
		super(var1);
	}

	public long player;
	public String factionName;
	public Organization org;
	public RType relation;
	public long factionLeader;
	public String factionLeaderName;
	public Government government;
	public String governmentString;
	public String factionDetails;
	public GUIOverlay background;
	public FactionLogoPanel factionLogoPanel;
	public FactionDetailsPanel factionDetailsPanel;
	public GUITextOverlay factionNameTextBox;
	
	
	public void loadInfo(FactionInfo info, long player) throws NumberFormatException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this.factionName = info.getName();
		this.org = info.getOrganization();
		this.governmentString = getGovernmentString(government);
		this.relation = info.getRelationshipWithFactionOrPlayer(player);
		this.factionLeaderName = Reflectors.getPlayerNameFromID(factionLeader);
		this.factionDetails = info.getFactionDetails();
	}
	
	public String getGovernmentString(Government gov) {
		String governmentString = null;
		
		if(gov == Government.ANARCHIST) {
			governmentString = "Anarchist";
		} else if(gov == Government.COMMUNIST) {
			governmentString = "Communist";
		} else if(gov == Government.DICTATORSHIP) {
			governmentString = "Dictatorship";
		} else if(gov == Government.DIRECT_DEMOCRACY) {
			governmentString = "Direct Democracy";
		}  else if(gov == Government.LIBERTARIAN) {
			governmentString = "Libertarian";
		} else if(gov == Government.MILITARY_JUNTA) {
			governmentString = "Military Junta";
		} else if(gov == Government.MONARCHY) {
			governmentString = "Monarchy";
		}  else if(gov == Government.OLIGARCHY) {
			governmentString = "Oligarchy";
		} else if(gov == Government.REPUBLIC) {
			governmentString = "Republic";
		} else if(gov == Government.TECHNOCRACY) {
			governmentString = "Technocracy";
		} else if(gov == Government.THEOCRACY) {
			governmentString = "Theocracy";
		} else if(gov == Government.THEOCRATIC_MONARCHY) {
			governmentString = "Theocratic Monarchy";
		} else if(gov == null) {
			governmentString = "No Government";
		}
		
		return governmentString;
	}
	
	public FactionControlManager getFactionManager() {
		return ((GameClientState) this.getState()).getGlobalGameControlManager().getIngameControlManager().getPlayerGameControlManager().getFactionControlManager();
	}

	public PlayerFactionController getFactionController() {
		return ((GameClientState) this.getState()).getPlayer().getFactionController();
	}
	
	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		if (this.needsReOrientation()) {
			this.doOrientation();
		}

		this.factionNameTextBox.getText().set(0, this.getFactionController().getFactionName());
		
		GlUtil.glPushMatrix();
		this.transform();
		this.drawAttached();
		GlUtil.glPopMatrix();
	}

	@Override
	public void onInit() {
		this.getFactionManager().addObserver(this);
		this.background = new GUIOverlay(Controller.getResLoader().getSprite("faction-personal-panel-gui-"), this.getState());
		//Faction Logo Panel
		this.factionLogoPanel = new FactionLogoPanel(this.getState(), 70.0F, 35.0F);
		this.factionLogoPanel.setPos(30.0F, 105.0F, 0.0F);
		this.factionLogoPanel.onInit();
		this.background.attach(factionLogoPanel);
		
		this.factionDetailsPanel = new FactionDetailsPanel(this.getState());
		
		this.factionNameTextBox = new GUITextOverlay(this.getState());
		
		this.attach(background);
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
