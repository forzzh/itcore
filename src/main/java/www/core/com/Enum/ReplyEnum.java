package www.core.com.Enum;

/**
 * 回复类型
 * @author zhaozhenyi
 *
 */
public enum ReplyEnum implements Enum {
	TopicReply{
		@Override
		public String show() {
			return "帖子回复";
		}
		
	},
	BookReply{
		@Override
		public String show() {
			return "图书回复";
		}		
	}
}
