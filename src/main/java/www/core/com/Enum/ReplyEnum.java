package www.core.com.Enum;

/**
 * �ظ�����
 * @author zhaozhenyi
 *
 */
public enum ReplyEnum implements Enum {
	TopicReply{
		@Override
		public String show() {
			return "���ӻظ�";
		}
		
	},
	BookReply{
		@Override
		public String show() {
			return "ͼ��ظ�";
		}		
	}
}
