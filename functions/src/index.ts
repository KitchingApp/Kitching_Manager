import { initializeApp } from 'firebase-admin/app';
import { 
  sendScheduleRejectMessage, 
  sendNoticeMessage 
} from './fcmFunctions';

// Firebase Admin SDK 초기화
initializeApp();

// Functions Export (독립 엔드포인트)
export { 
  sendScheduleRejectMessage,  // 스케줄 거절 알림
  sendNoticeMessage           // 공지사항 알림 (일괄 전송)
};
