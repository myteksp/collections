package com.gf.collections.iter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


import com.gf.collections.GfCollection;
import com.gf.collections.LinkedGfCollection;

public final class CollectionIterator {
	private static final <T> void iterate(
			final LinkedList<T> collection, 
			final CollectionConsumer<T> consumer){
		final Iterator<T> iter = collection.iterator();
		for (int i = 0; i < Integer.MAX_VALUE; i++) 
			try {
				consumer.consume(iter.next(), i);
			}catch(final NoSuchElementException ex) {
				return;
			}
	}
	@SuppressWarnings("unchecked")
	public static final <T> void iterate(
			final GfCollection<T> collection, 
			final CollectionConsumer<T> consumer) {
		if (consumer == null)
			return;
		if (collection == null)
			return;
		if (collection instanceof LinkedGfCollection) {
			iterate((LinkedList<T>)collection, consumer);
		}else {
			final int len = collection.size();
			switch(len) {
			case 0:
				GeneratedIterators.iter0(collection, consumer);
				return;
			case 1:
				GeneratedIterators.iter1(collection, consumer);
				return;
			case 2:
				GeneratedIterators.iter2(collection, consumer);
				return;
			case 3:
				GeneratedIterators.iter3(collection, consumer);
				return;
			case 4:
				GeneratedIterators.iter4(collection, consumer);
				return;
			case 5:
				GeneratedIterators.iter5(collection, consumer);
				return;
			case 6:
				GeneratedIterators.iter6(collection, consumer);
				return;
			case 7:
				GeneratedIterators.iter7(collection, consumer);
				return;
			case 8:
				GeneratedIterators.iter8(collection, consumer);
				return;
			case 9:
				GeneratedIterators.iter9(collection, consumer);
				return;
			case 10:
				GeneratedIterators.iter10(collection, consumer);
				return;
			case 11:
				GeneratedIterators.iter11(collection, consumer);
				return;
			case 12:
				GeneratedIterators.iter12(collection, consumer);
				return;
			case 13:
				GeneratedIterators.iter13(collection, consumer);
				return;
			case 14:
				GeneratedIterators.iter14(collection, consumer);
				return;
			case 15:
				GeneratedIterators.iter15(collection, consumer);
				return;
			case 16:
				GeneratedIterators.iter16(collection, consumer);
				return;
			case 17:
				GeneratedIterators.iter17(collection, consumer);
				return;
			case 18:
				GeneratedIterators.iter18(collection, consumer);
				return;
			case 19:
				GeneratedIterators.iter19(collection, consumer);
				return;
			case 20:
				GeneratedIterators.iter20(collection, consumer);
				return;
			case 21:
				GeneratedIterators.iter21(collection, consumer);
				return;
			case 22:
				GeneratedIterators.iter22(collection, consumer);
				return;
			case 23:
				GeneratedIterators.iter23(collection, consumer);
				return;
			case 24:
				GeneratedIterators.iter24(collection, consumer);
				return;
			case 25:
				GeneratedIterators.iter25(collection, consumer);
				return;
			case 26:
				GeneratedIterators.iter26(collection, consumer);
				return;
			case 27:
				GeneratedIterators.iter27(collection, consumer);
				return;
			case 28:
				GeneratedIterators.iter28(collection, consumer);
				return;
			case 29:
				GeneratedIterators.iter29(collection, consumer);
				return;
			case 30:
				GeneratedIterators.iter30(collection, consumer);
				return;
			case 31:
				GeneratedIterators.iter31(collection, consumer);
				return;
			case 32:
				GeneratedIterators.iter32(collection, consumer);
				return;
			case 33:
				GeneratedIterators.iter33(collection, consumer);
				return;
			case 34:
				GeneratedIterators.iter34(collection, consumer);
				return;
			case 35:
				GeneratedIterators.iter35(collection, consumer);
				return;
			case 36:
				GeneratedIterators.iter36(collection, consumer);
				return;
			case 37:
				GeneratedIterators.iter37(collection, consumer);
				return;
			case 38:
				GeneratedIterators.iter38(collection, consumer);
				return;
			case 39:
				GeneratedIterators.iter39(collection, consumer);
				return;
			case 40:
				GeneratedIterators.iter40(collection, consumer);
				return;
			case 41:
				GeneratedIterators.iter41(collection, consumer);
				return;
			case 42:
				GeneratedIterators.iter42(collection, consumer);
				return;
			case 43:
				GeneratedIterators.iter43(collection, consumer);
				return;
			case 44:
				GeneratedIterators.iter44(collection, consumer);
				return;
			case 45:
				GeneratedIterators.iter45(collection, consumer);
				return;
			case 46:
				GeneratedIterators.iter46(collection, consumer);
				return;
			case 47:
				GeneratedIterators.iter47(collection, consumer);
				return;
			case 48:
				GeneratedIterators.iter48(collection, consumer);
				return;
			case 49:
				GeneratedIterators.iter49(collection, consumer);
				return;
			case 50:
				GeneratedIterators.iter50(collection, consumer);
				return;
			case 51:
				GeneratedIterators.iter51(collection, consumer);
				return;
			case 52:
				GeneratedIterators.iter52(collection, consumer);
				return;
			case 53:
				GeneratedIterators.iter53(collection, consumer);
				return;
			case 54:
				GeneratedIterators.iter54(collection, consumer);
				return;
			case 55:
				GeneratedIterators.iter55(collection, consumer);
				return;
			case 56:
				GeneratedIterators.iter56(collection, consumer);
				return;
			case 57:
				GeneratedIterators.iter57(collection, consumer);
				return;
			case 58:
				GeneratedIterators.iter58(collection, consumer);
				return;
			case 59:
				GeneratedIterators.iter59(collection, consumer);
				return;
			case 60:
				GeneratedIterators.iter60(collection, consumer);
				return;
			case 61:
				GeneratedIterators.iter61(collection, consumer);
				return;
			case 62:
				GeneratedIterators.iter62(collection, consumer);
				return;
			case 63:
				GeneratedIterators.iter63(collection, consumer);
				return;
			case 64:
				GeneratedIterators.iter64(collection, consumer);
				return;
			case 65:
				GeneratedIterators.iter65(collection, consumer);
				return;
			case 66:
				GeneratedIterators.iter66(collection, consumer);
				return;
			case 67:
				GeneratedIterators.iter67(collection, consumer);
				return;
			case 68:
				GeneratedIterators.iter68(collection, consumer);
				return;
			case 69:
				GeneratedIterators.iter69(collection, consumer);
				return;
			case 70:
				GeneratedIterators.iter70(collection, consumer);
				return;
			case 71:
				GeneratedIterators.iter71(collection, consumer);
				return;
			case 72:
				GeneratedIterators.iter72(collection, consumer);
				return;
			case 73:
				GeneratedIterators.iter73(collection, consumer);
				return;
			case 74:
				GeneratedIterators.iter74(collection, consumer);
				return;
			case 75:
				GeneratedIterators.iter75(collection, consumer);
				return;
			case 76:
				GeneratedIterators.iter76(collection, consumer);
				return;
			case 77:
				GeneratedIterators.iter77(collection, consumer);
				return;
			case 78:
				GeneratedIterators.iter78(collection, consumer);
				return;
			case 79:
				GeneratedIterators.iter79(collection, consumer);
				return;
			case 80:
				GeneratedIterators.iter80(collection, consumer);
				return;
			case 81:
				GeneratedIterators.iter81(collection, consumer);
				return;
			case 82:
				GeneratedIterators.iter82(collection, consumer);
				return;
			case 83:
				GeneratedIterators.iter83(collection, consumer);
				return;
			case 84:
				GeneratedIterators.iter84(collection, consumer);
				return;
			case 85:
				GeneratedIterators.iter85(collection, consumer);
				return;
			case 86:
				GeneratedIterators.iter86(collection, consumer);
				return;
			case 87:
				GeneratedIterators.iter87(collection, consumer);
				return;
			case 88:
				GeneratedIterators.iter88(collection, consumer);
				return;
			case 89:
				GeneratedIterators.iter89(collection, consumer);
				return;
			case 90:
				GeneratedIterators.iter90(collection, consumer);
				return;
			case 91:
				GeneratedIterators.iter91(collection, consumer);
				return;
			case 92:
				GeneratedIterators.iter92(collection, consumer);
				return;
			case 93:
				GeneratedIterators.iter93(collection, consumer);
				return;
			case 94:
				GeneratedIterators.iter94(collection, consumer);
				return;
			case 95:
				GeneratedIterators.iter95(collection, consumer);
				return;
			case 96:
				GeneratedIterators.iter96(collection, consumer);
				return;
			case 97:
				GeneratedIterators.iter97(collection, consumer);
				return;
			case 98:
				GeneratedIterators.iter98(collection, consumer);
				return;
			case 99:
				GeneratedIterators.iter99(collection, consumer);
				return;
			case 100:
				GeneratedIterators.iter100(collection, consumer);
				return;
			case 101:
				GeneratedIterators.iter101(collection, consumer);
				return;
			case 102:
				GeneratedIterators.iter102(collection, consumer);
				return;
			case 103:
				GeneratedIterators.iter103(collection, consumer);
				return;
			case 104:
				GeneratedIterators.iter104(collection, consumer);
				return;
			case 105:
				GeneratedIterators.iter105(collection, consumer);
				return;
			case 106:
				GeneratedIterators.iter106(collection, consumer);
				return;
			case 107:
				GeneratedIterators.iter107(collection, consumer);
				return;
			case 108:
				GeneratedIterators.iter108(collection, consumer);
				return;
			case 109:
				GeneratedIterators.iter109(collection, consumer);
				return;
			case 110:
				GeneratedIterators.iter110(collection, consumer);
				return;
			case 111:
				GeneratedIterators.iter111(collection, consumer);
				return;
			case 112:
				GeneratedIterators.iter112(collection, consumer);
				return;
			case 113:
				GeneratedIterators.iter113(collection, consumer);
				return;
			case 114:
				GeneratedIterators.iter114(collection, consumer);
				return;
			case 115:
				GeneratedIterators.iter115(collection, consumer);
				return;
			case 116:
				GeneratedIterators.iter116(collection, consumer);
				return;
			case 117:
				GeneratedIterators.iter117(collection, consumer);
				return;
			case 118:
				GeneratedIterators.iter118(collection, consumer);
				return;
			case 119:
				GeneratedIterators.iter119(collection, consumer);
				return;
			case 120:
				GeneratedIterators.iter120(collection, consumer);
				return;
			case 121:
				GeneratedIterators.iter121(collection, consumer);
				return;
			case 122:
				GeneratedIterators.iter122(collection, consumer);
				return;
			case 123:
				GeneratedIterators.iter123(collection, consumer);
				return;
			case 124:
				GeneratedIterators.iter124(collection, consumer);
				return;
			case 125:
				GeneratedIterators.iter125(collection, consumer);
				return;
			case 126:
				GeneratedIterators.iter126(collection, consumer);
				return;
			case 127:
				GeneratedIterators.iter127(collection, consumer);
				return;
			case 128:
				GeneratedIterators.iter128(collection, consumer);
				return;
			case 129:
				GeneratedIterators.iter129(collection, consumer);
				return;
			case 130:
				GeneratedIterators.iter130(collection, consumer);
				return;
			case 131:
				GeneratedIterators.iter131(collection, consumer);
				return;
			case 132:
				GeneratedIterators.iter132(collection, consumer);
				return;
			case 133:
				GeneratedIterators.iter133(collection, consumer);
				return;
			case 134:
				GeneratedIterators.iter134(collection, consumer);
				return;
			case 135:
				GeneratedIterators.iter135(collection, consumer);
				return;
			case 136:
				GeneratedIterators.iter136(collection, consumer);
				return;
			case 137:
				GeneratedIterators.iter137(collection, consumer);
				return;
			case 138:
				GeneratedIterators.iter138(collection, consumer);
				return;
			case 139:
				GeneratedIterators.iter139(collection, consumer);
				return;
			case 140:
				GeneratedIterators.iter140(collection, consumer);
				return;
			case 141:
				GeneratedIterators.iter141(collection, consumer);
				return;
			case 142:
				GeneratedIterators.iter142(collection, consumer);
				return;
			case 143:
				GeneratedIterators.iter143(collection, consumer);
				return;
			case 144:
				GeneratedIterators.iter144(collection, consumer);
				return;
			case 145:
				GeneratedIterators.iter145(collection, consumer);
				return;
			case 146:
				GeneratedIterators.iter146(collection, consumer);
				return;
			case 147:
				GeneratedIterators.iter147(collection, consumer);
				return;
			case 148:
				GeneratedIterators.iter148(collection, consumer);
				return;
			case 149:
				GeneratedIterators.iter149(collection, consumer);
				return;
			case 150:
				GeneratedIterators.iter150(collection, consumer);
				return;
			case 151:
				GeneratedIterators.iter151(collection, consumer);
				return;
			case 152:
				GeneratedIterators.iter152(collection, consumer);
				return;
			case 153:
				GeneratedIterators.iter153(collection, consumer);
				return;
			case 154:
				GeneratedIterators.iter154(collection, consumer);
				return;
			case 155:
				GeneratedIterators.iter155(collection, consumer);
				return;
			case 156:
				GeneratedIterators.iter156(collection, consumer);
				return;
			case 157:
				GeneratedIterators.iter157(collection, consumer);
				return;
			case 158:
				GeneratedIterators.iter158(collection, consumer);
				return;
			case 159:
				GeneratedIterators.iter159(collection, consumer);
				return;
			case 160:
				GeneratedIterators.iter160(collection, consumer);
				return;
			case 161:
				GeneratedIterators.iter161(collection, consumer);
				return;
			case 162:
				GeneratedIterators.iter162(collection, consumer);
				return;
			case 163:
				GeneratedIterators.iter163(collection, consumer);
				return;
			case 164:
				GeneratedIterators.iter164(collection, consumer);
				return;
			case 165:
				GeneratedIterators.iter165(collection, consumer);
				return;
			case 166:
				GeneratedIterators.iter166(collection, consumer);
				return;
			case 167:
				GeneratedIterators.iter167(collection, consumer);
				return;
			case 168:
				GeneratedIterators.iter168(collection, consumer);
				return;
			case 169:
				GeneratedIterators.iter169(collection, consumer);
				return;
			case 170:
				GeneratedIterators.iter170(collection, consumer);
				return;
			case 171:
				GeneratedIterators.iter171(collection, consumer);
				return;
			case 172:
				GeneratedIterators.iter172(collection, consumer);
				return;
			case 173:
				GeneratedIterators.iter173(collection, consumer);
				return;
			case 174:
				GeneratedIterators.iter174(collection, consumer);
				return;
			case 175:
				GeneratedIterators.iter175(collection, consumer);
				return;
			case 176:
				GeneratedIterators.iter176(collection, consumer);
				return;
			case 177:
				GeneratedIterators.iter177(collection, consumer);
				return;
			case 178:
				GeneratedIterators.iter178(collection, consumer);
				return;
			case 179:
				GeneratedIterators.iter179(collection, consumer);
				return;
			case 180:
				GeneratedIterators.iter180(collection, consumer);
				return;
			case 181:
				GeneratedIterators.iter181(collection, consumer);
				return;
			case 182:
				GeneratedIterators.iter182(collection, consumer);
				return;
			case 183:
				GeneratedIterators.iter183(collection, consumer);
				return;
			case 184:
				GeneratedIterators.iter184(collection, consumer);
				return;
			case 185:
				GeneratedIterators.iter185(collection, consumer);
				return;
			case 186:
				GeneratedIterators.iter186(collection, consumer);
				return;
			case 187:
				GeneratedIterators.iter187(collection, consumer);
				return;
			case 188:
				GeneratedIterators.iter188(collection, consumer);
				return;
			case 189:
				GeneratedIterators.iter189(collection, consumer);
				return;
			case 190:
				GeneratedIterators.iter190(collection, consumer);
				return;
			case 191:
				GeneratedIterators.iter191(collection, consumer);
				return;
			case 192:
				GeneratedIterators.iter192(collection, consumer);
				return;
			case 193:
				GeneratedIterators.iter193(collection, consumer);
				return;
			case 194:
				GeneratedIterators.iter194(collection, consumer);
				return;
			case 195:
				GeneratedIterators.iter195(collection, consumer);
				return;
			case 196:
				GeneratedIterators.iter196(collection, consumer);
				return;
			case 197:
				GeneratedIterators.iter197(collection, consumer);
				return;
			case 198:
				GeneratedIterators.iter198(collection, consumer);
				return;
			case 199:
				GeneratedIterators.iter199(collection, consumer);
				return;
			case 200:
				GeneratedIterators.iter200(collection, consumer);
				return;
			case 201:
				GeneratedIterators.iter201(collection, consumer);
				return;
			case 202:
				GeneratedIterators.iter202(collection, consumer);
				return;
			case 203:
				GeneratedIterators.iter203(collection, consumer);
				return;
			case 204:
				GeneratedIterators.iter204(collection, consumer);
				return;
			case 205:
				GeneratedIterators.iter205(collection, consumer);
				return;
			case 206:
				GeneratedIterators.iter206(collection, consumer);
				return;
			case 207:
				GeneratedIterators.iter207(collection, consumer);
				return;
			case 208:
				GeneratedIterators.iter208(collection, consumer);
				return;
			case 209:
				GeneratedIterators.iter209(collection, consumer);
				return;
			case 210:
				GeneratedIterators.iter210(collection, consumer);
				return;
			case 211:
				GeneratedIterators.iter211(collection, consumer);
				return;
			case 212:
				GeneratedIterators.iter212(collection, consumer);
				return;
			case 213:
				GeneratedIterators.iter213(collection, consumer);
				return;
			case 214:
				GeneratedIterators.iter214(collection, consumer);
				return;
			case 215:
				GeneratedIterators.iter215(collection, consumer);
				return;
			case 216:
				GeneratedIterators.iter216(collection, consumer);
				return;
			case 217:
				GeneratedIterators.iter217(collection, consumer);
				return;
			case 218:
				GeneratedIterators.iter218(collection, consumer);
				return;
			case 219:
				GeneratedIterators.iter219(collection, consumer);
				return;
			case 220:
				GeneratedIterators.iter220(collection, consumer);
				return;
			case 221:
				GeneratedIterators.iter221(collection, consumer);
				return;
			case 222:
				GeneratedIterators.iter222(collection, consumer);
				return;
			case 223:
				GeneratedIterators.iter223(collection, consumer);
				return;
			case 224:
				GeneratedIterators.iter224(collection, consumer);
				return;
			case 225:
				GeneratedIterators.iter225(collection, consumer);
				return;
			case 226:
				GeneratedIterators.iter226(collection, consumer);
				return;
			case 227:
				GeneratedIterators.iter227(collection, consumer);
				return;
			case 228:
				GeneratedIterators.iter228(collection, consumer);
				return;
			case 229:
				GeneratedIterators.iter229(collection, consumer);
				return;
			case 230:
				GeneratedIterators.iter230(collection, consumer);
				return;
			case 231:
				GeneratedIterators.iter231(collection, consumer);
				return;
			case 232:
				GeneratedIterators.iter232(collection, consumer);
				return;
			case 233:
				GeneratedIterators.iter233(collection, consumer);
				return;
			case 234:
				GeneratedIterators.iter234(collection, consumer);
				return;
			case 235:
				GeneratedIterators.iter235(collection, consumer);
				return;
			case 236:
				GeneratedIterators.iter236(collection, consumer);
				return;
			case 237:
				GeneratedIterators.iter237(collection, consumer);
				return;
			case 238:
				GeneratedIterators.iter238(collection, consumer);
				return;
			case 239:
				GeneratedIterators.iter239(collection, consumer);
				return;
			case 240:
				GeneratedIterators.iter240(collection, consumer);
				return;
			case 241:
				GeneratedIterators.iter241(collection, consumer);
				return;
			case 242:
				GeneratedIterators.iter242(collection, consumer);
				return;
			case 243:
				GeneratedIterators.iter243(collection, consumer);
				return;
			case 244:
				GeneratedIterators.iter244(collection, consumer);
				return;
			case 245:
				GeneratedIterators.iter245(collection, consumer);
				return;
			case 246:
				GeneratedIterators.iter246(collection, consumer);
				return;
			case 247:
				GeneratedIterators.iter247(collection, consumer);
				return;
			case 248:
				GeneratedIterators.iter248(collection, consumer);
				return;
			case 249:
				GeneratedIterators.iter249(collection, consumer);
				return;
			case 250:
				GeneratedIterators.iter250(collection, consumer);
				return;
			case 251:
				GeneratedIterators.iter251(collection, consumer);
				return;
			case 252:
				GeneratedIterators.iter252(collection, consumer);
				return;
			case 253:
				GeneratedIterators.iter253(collection, consumer);
				return;
			case 254:
				GeneratedIterators.iter254(collection, consumer);
				return;
			case 255:
				GeneratedIterators.iter255(collection, consumer);
				return;
			default:
				GeneratedIterators.iter255(collection, consumer);
				for (int i = 255; i < len; i++)
					consumer.consume(collection.get(i), i);
				return;
			}
		}
	}

}
