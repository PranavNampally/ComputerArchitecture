package processor.pipeline;

import generic.Simulator;
import processor.Processor;

import generic.Instruction;
import generic.Instruction.OperationType;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			System.out.println("RW Stage:");
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			Instruction inst=MA_RW_Latch.instruction;
			MA_RW_Latch.setInstruction(inst);
			OperationType op_type=inst.getOperationType();

			OperationType[] all_operations= OperationType.values();

			int rd_no=inst.getDestinationOperand().getValue();
			int alu_result=MA_RW_Latch.getAluResult();
			int ld_Result=MA_RW_Latch.getldResult();

			if(op_type==all_operations[22]){//ld
				containingProcessor.getRegisterFile().setValue(rd_no, ld_Result);
			}
			else if(op_type==all_operations[23]||op_type==all_operations[24]||op_type==all_operations[25]||op_type==all_operations[26]||op_type==all_operations[27] ||op_type==all_operations[28]){
				//st, beq, bgt, bne, blt
			}
			else if(op_type==all_operations[29]){//end
				Simulator.setSimulationComplete(true);
					return;
			}
			else{
				containingProcessor.getRegisterFile().setValue(rd_no, alu_result);
			}
			System.out.println(containingProcessor.getRegisterFile().getContentsAsString());

			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
		
	}

}
