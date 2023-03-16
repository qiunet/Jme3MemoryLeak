package org.example;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.PhysicsSpace;


/***
 * room跟场景相关的内容
 *
 * @author qiunet
 * 2021/10/19 10:59
 **/
public class Room extends BaseAppState {
	protected final SimpleApplication application;
	protected PhysicsSpace physicsSpace;

	protected final long roomId;
	/**
	 * 创建指定大小的世界
	 * @param roomId
	 */
	protected Room(SimpleApplication application, long roomId) {
		this.physicsSpace = new PhysicsSpace(PhysicsSpace.BroadphaseType.DBVT);
		application.getStateManager().attach(this);
		this.application = application;
		this.roomId = roomId;

		System.out.println("create room id: " + roomId);
	}

	public void destroy() {
		this.application.getStateManager().detach(this);
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		System.out.println("destroy room id: " + roomId);
		this.physicsSpace.destroy();
	}

	@Override
	public void update(float tpf) {
		this.physicsSpace.update(tpf);
	}

	@Override
	protected void initialize(Application app) {
		this.physicsSpace.setMaxSubSteps(Math.round (60 / app.getTimer().getFrameRate()));
		this.physicsSpace.setAccuracy(1 / 30f);

		if (this.isEnabled()) {
			this.onEnable();
		}
	}

	@Override
	protected void onEnable() {}

	@Override
	protected void onDisable() {}

	@Override
	protected void cleanup(Application app) {}
}
