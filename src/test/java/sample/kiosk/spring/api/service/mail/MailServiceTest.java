package sample.kiosk.spring.api.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.kiosk.spring.client.mail.MailSendClient;
import sample.kiosk.spring.domain.history.mail.MailSendHistory;
import sample.kiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    //@Spy
    // 해당 컴포넌트에 여러개의 메서드가 있고 mailService 에서 여러개를 다 호출할때
    // 이중 내가 원하는것만 stubbing 하고 다른 호출메서드는 원본기능대로 동작하게 하고싶을때 spy 사용??
    // spy는 실제객체기반으로 만들어지고 mock은 모두 대체하는 개념, spy쓰면 when 말고 do~ 사용
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);

        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        Assertions.assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

}