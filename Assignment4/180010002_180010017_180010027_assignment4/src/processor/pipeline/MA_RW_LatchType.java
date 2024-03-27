package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	Instruction instruction;
	int load_result;
	int alu_result;
	boolean NOP;
	
	public MA_RW_LatchType() {
		RW_enable = false;
		NOP = false;
	}
	
	public MA_RW_LatchType(boolean rW_enable) {
		RW_enable = rW_enable;
		NOP = false;
	}
	
	public MA_RW_LatchType(boolean rW_enable, int load_result) {
		RW_enable = rW_enable;
		NOP = false;
	}

	public MA_RW_LatchType(boolean rW_enable, int load_result, int alu_result) {
		RW_enable = rW_enable;
		NOP = false;
	}

	public MA_RW_LatchType(boolean rW_enable, int load_result, int alu_result, boolean nop) {
		RW_enable = rW_enable;
		NOP = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public Instruction getInstruction(int inst_num) {
		return instruction;
	}

	public void setInstruction(Instruction inst) {
		instruction = inst;
	}

	public void setLoad_result(int result) {
		load_result = result;
	}

	public int getLoad_result() {
		return load_result;
	}

	public int getALU_result() {
		return alu_result;
	}

	public void setALU_result(int result) {
		alu_result = result;
	}
	
	public boolean getIsNOP() {
		return NOP;
	}
	
	public void setIsNOP(boolean is_NOP) {
		NOP = is_NOP;
	}
}
