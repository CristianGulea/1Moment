export interface Message{
  id :number,
  userId: number,
  groupId: number,
  parentMessageId: string,
  title?: string,
  content: string,
  publishDate: Date,
  username?: string,
  groupName?: string,
  liked?: boolean,
  likeCount?: number,
}

