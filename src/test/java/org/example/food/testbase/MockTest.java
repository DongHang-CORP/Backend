package org.example.food.testbase;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)@Disabled // 이 테스트 클래스는 비활성화
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureMockMvc // MockMvc를 자동 설정
public abstract class MockTest {
    @Autowired
    protected MockMvc mockMvc;
}
