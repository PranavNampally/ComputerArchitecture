package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Statistics;
import generic.Operand.OperandType;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public static char flip(char c) {
		return (c == '0') ? '1' : '0';
	}

	public static String twosComplement(String bin) {
		String twos = "", ones = "";
		for (int i = 0; i < bin.length() && true; i++) {
			ones += flip(bin.charAt(i));
		}

		StringBuilder builder = new StringBuilder(ones);
		boolean b = false;
		for (int i = ones.length() - 1; i > 0 && i > -2; i--) {
			if (ones.charAt(i) == '1') {
				builder.setCharAt(i, '0');
			} else {
				builder.setCharAt(i, '1');
				b = true;
				break;
			}
		}
		if (!b) {
			builder.append("1", 0, 7);
		}
		twos = builder.toString();
		return twos;
	}
	
	public static boolean checkConflict(Instruction instruction, int reg_1, int reg_2) {
		int inst_ordinal = instruction != null && instruction.getOperationType() != null ? instruction.getOperationType().ordinal() : 1000;
		if ((inst_ordinal <= 21 && inst_ordinal % 2 == 0) || (inst_ordinal <= 21 && inst_ordinal % 2 != 0) || inst_ordinal == 22 || inst_ordinal == 23) {
			int dest_reg = instruction != null ? instruction.getDestinationOperand().getValue() : -1;
			if (reg_1 == dest_reg || reg_2 == dest_reg) {
				return true;
			} else {
				return false;
			}
		} else return false;
	}
	
	public boolean checkConflictWithDivision(int reg_1, int reg_2) {
		Instruction instruction_ex_stage = OF_EX_Latch.getInstruction();
		Instruction instruction_ma_stage = EX_MA_Latch.getInstruction();
		Instruction instruction_rw_stage = MA_RW_Latch.getInstruction();
		if (reg_1 == 31 || reg_2 == 31) {
			int inst_ex_ordinal = instruction_ex_stage != null && instruction_ex_stage.getOperationType() != null ? instruction_ex_stage.getOperationType().ordinal() : 1000;
			int inst_ma_ordinal = instruction_ma_stage != null && instruction_ma_stage.getOperationType() != null ? instruction_ma_stage.getOperationType().ordinal() : 1000;
			int inst_rw_ordinal = instruction_rw_stage != null && instruction_rw_stage.getOperationType() != null ? instruction_rw_stage.getOperationType().ordinal() : 1000;
			if (inst_ex_ordinal == 6 || inst_ex_ordinal == 7 || inst_ma_ordinal == 6 || inst_ma_ordinal == 7 || inst_rw_ordinal == 6 || inst_rw_ordinal == 7) {
				System.out.println("Conflict in division");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void conflictBubblePCModify () {
		System.out.println("Conflict Observed");
		IF_EnableLatch.setIF_enable(false);
		OF_EX_Latch.setIsNOP(true);
	}
	
	private static String toBinaryOfSpecificPrecision(int num, int lenOfTargetString) {
		String binary = String.format("%" + lenOfTargetString + "s", Integer.toBinaryString(num)).replace(' ', '0');
		return binary;
	}
	
	private static int toSignedInteger(String binary) {
		int n = 32 - binary.length();
        char[] sign_ext = new char[n];
        Arrays.fill(sign_ext, binary.charAt(0));
        int signedInteger = (int) Long.parseLong(new String(sign_ext) + binary, 2);
        return signedInteger;
	}

	private void loopAround(int num) {
		for (int i = 0; i < num; i += 1)
			toSignedInteger(toBinaryOfSpecificPrecision(i, 20));
	}
 	
	public void performOF() {
		if (IF_OF_Latch.isOF_enable()) {
			Statistics.setNumberOfOFInstructions(Statistics.getNumberOfOFInstructions() + 1);
			OperationType[] operationType = OperationType.values();
			String instruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());
			System.out.println("OF is enabled with instruction: " + instruction + "..");
			while (instruction.length() != 32) {
				instruction = "0" + instruction;
			}
			String opcode = instruction.substring(0, 5);
			int type_operation = Integer.parseInt(opcode, 2);
			OperationType operation = operationType[type_operation];
			
			if (operation.ordinal() == 24 || operation.ordinal() == 25 || operation.ordinal() == 26 || operation.ordinal() == 27 || operation.ordinal() == 28 ) {
				IF_EnableLatch.setIF_enable(false);
			}
			
			int signedInt = toSignedInteger("001");
			String binaryNum = toBinaryOfSpecificPrecision(signedInt, 5);
			binaryNum = toBinaryOfSpecificPrecision(numBits, 5);
			signedInt = toSignedInteger(binaryNum);
			loopAround(20);
			
			boolean conflict_inst = false;
			Instruction instruction_ex_stage = OF_EX_Latch.getInstruction();
			Instruction instruction_ma_stage = EX_MA_Latch.getInstruction();
			Instruction instruction_rw_stage = MA_RW_Latch.getInstruction();
			Instruction inst = new Instruction();
			switch (operation) {
			case add:
			case sub:
			case mul:
			case div:
			case and:
			case or:
			case xor:
			case slt:
			case sll:
			case srl:
			case sra:
				Operand rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				int registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
				rs1.setValue(registerNo);

				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				int registerNo2 = Integer.parseInt(instruction.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				if (checkConflict(instruction_ex_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (checkConflict(instruction_ma_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (checkConflict(instruction_rw_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (checkConflictWithDivision(registerNo, registerNo2)) {
					conflict_inst = true;
				}
				if (conflict_inst) {
					this.conflictBubblePCModify();
					break;
				}

				Operand rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instruction.substring(15, 20), 2);
				rd.setValue(registerNo);

				inst.setOperationType(operationType[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;

			case end:
				inst.setOperationType(operationType[type_operation]);
				IF_EnableLatch.setIF_enable(false);
				break;
			case jmp:
				Operand op = new Operand();
				String imm = instruction.substring(10, 32);
				int imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				if (imm_val != 0) {
					op.setOperandType(OperandType.Immediate);
					op.setValue(imm_val);
				} else {
					registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
					op.setOperandType(OperandType.Register);
					op.setValue(registerNo);
				}

				inst.setOperationType(operationType[type_operation]);
				inst.setDestinationOperand(op);
				break;

			case beq:
			case bne:
			case blt:
			case bgt:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
				rs1.setValue(registerNo);
				
				// destination register
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				registerNo2 = Integer.parseInt(instruction.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				
				if (checkConflict(instruction_ex_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (checkConflict(instruction_ma_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (checkConflict(instruction_rw_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (checkConflictWithDivision(registerNo, registerNo2)) {
					conflict_inst = true;
				}
				if (conflict_inst) {
					this.conflictBubblePCModify();
					break;
				}
				
	

				// Immediate value
				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				imm = instruction.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rd.setValue(imm_val);
				
				inst.setOperationType(operationType[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;

			default:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
				rs1.setValue(registerNo);
				if (checkConflict(instruction_ex_stage, registerNo, registerNo)) {
					conflict_inst = true;
				}	
				if (checkConflict(instruction_ma_stage, registerNo, registerNo)) {
					conflict_inst = true;
				}
				if (checkConflict(instruction_rw_stage, registerNo, registerNo)) {
					conflict_inst = true;
				}
				if (checkConflictWithDivision(registerNo, registerNo)) {
					conflict_inst = true;
				}
					
				if (conflict_inst) {
					this.conflictBubblePCModify();
					break;
				}

				// Destination register
				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instruction.substring(10, 15), 2);
				rd.setValue(registerNo);

				// Immediate values
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Immediate);
				imm = instruction.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rs2.setValue(imm_val);
				inst.setOperationType(operationType[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			}
			OF_EX_Latch.setInstruction(inst);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
