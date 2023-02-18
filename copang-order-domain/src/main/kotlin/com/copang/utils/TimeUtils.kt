package com.copang.utils

import com.copang.constants.DateConstants
import java.time.*

/**
 * LocalDateTime.now() 대신 TimeUtils.now()를 사용한다.
 * LocalDateTime.now()가 사용된 코드를 테스트할 때 setNow()를 통해서 편하게 테스트할 수 있다.
 */
object TimeUtils {
    private var clock = Clock.systemDefaultZone()

    fun now(): LocalDateTime = LocalDateTime.now(clock)

    fun reset() {
        clock = Clock.systemDefaultZone()
    }

    fun setNow(
        year: Int = now().year,
        month: Int = now().monthValue,
        day: Int = now().dayOfMonth,
        hour: Int = now().hour,
        minute: Int = now().minute,
        second: Int = now().second,
        nanoSecond: Int = now().nano,
        zone: ZoneId = DateConstants.ZONE_KST,
    ) {
        clock = Clock.fixed(
            Instant.from(ZonedDateTime.of(year, month, day, hour, minute, second, nanoSecond, zone)),
            zone,
        )!!
    }
}
