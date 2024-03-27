package processor.pipeline;

public class EX_IF_LatchType {
	
	int branchTarget=-1;
	boolean isBranchTaken=false;
	boolean IF_enable;
	public EX_IF_LatchType(){
		IF_enable=true;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}
	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
	public int getbranchTarget(){
		return branchTarget;
	}
	public void setbranchTarget(int bt){
		this.branchTarget=bt;
	}
	public boolean getIsBranchTaken(){
		return isBranchTaken;
	}
	public void setIsBranchTarget(boolean ibt){
		this.isBranchTaken=ibt;
	}

}
