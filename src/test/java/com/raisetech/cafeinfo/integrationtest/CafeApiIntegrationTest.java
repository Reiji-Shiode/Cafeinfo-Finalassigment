package com.raisetech.cafeinfo.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CafeApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    //Read処理結合テスト
    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void カフェ情報が全件取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                            {
                                "id":1,
                                "name":"Starbucks Reserve Roastery",
                                "place":"中目黒",
                                "regularHoliday":"年中無休",
                                "openingHour":"7時-22時",
                                "numberOfSeat":300,
                                "birthplace":"シアトル"
                            },
                            {
                                "id":2,
                                "name":"nadoya no katte",
                                "place":"代々木上原",
                                "regularHoliday":"月、火、水、木",
                                "openingHour":"9時-18時",
                                "numberOfSeat":15,
                                "birthplace":"代々木上原"
                            },
                            {
                                "id":3,
                                "name":"Streamer Coffee Company",
                                "place":"原宿",
                                "regularHoliday":"年中無休",
                                "openingHour":"8時-20時",
                                "numberOfSeat":56,
                                "birthplace":"原宿"
                            },
                            {
                                "id":4,
                                "name":"ブルーボトルコーヒー清澄白河フラッグシップカフェ",
                                "place":"清澄白河",
                                "regularHoliday":"年中無休",
                                "openingHour":"8時-19時",
                                "numberOfSeat":47,
                                "birthplace":"カリフォルニア"
                            },
                            {
                                "id":5,
                                "name":"ARC",
                                "place":"蔵前",
                                "regularHoliday":"年中無休",
                                "openingHour":"平日10時-23時 休日8時半-23時",
                                "numberOfSeat":52,
                                "birthplace":"蔵前"
                            },
                            {
                                "id":6,
                                "name":"T.Y.HARBOR",
                                "place":"天王洲アイル",
                                "regularHoliday":"年中無休",
                                "openingHour":"11時半-22時",
                                "numberOfSeat":350,
                                "birthplace":"天王洲アイル"
                            },
                            {
                                "id":7,
                                "name":"No.13 Cafe",
                                "place":"新宿",
                                "regularHoliday":"日",
                                "openingHour":"11時-16時半",
                                "numberOfSeat":30,
                                "birthplace":"新宿"
                            }
                        ]"""));
    }

    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void クエリ文字列を用いて特定の場所を指定すると該当するカフェ情報が取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes?place=清澄白河"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                            {
                                "id":4,
                                "name":"ブルーボトルコーヒー清澄白河フラッグシップカフェ",
                                "place":"清澄白河",
                                "regularHoliday":"年中無休",
                                "openingHour":"8時-19時",
                                "numberOfSeat":47,
                                "birthplace":"カリフォルニア"
                            }
                        ]
                        """));
    }

    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void 指定したIDのカフェ情報が存在する時にそのカフェ情報が取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                        "id":1,
                        "name":"Starbucks Reserve Roastery",
                        "place":"中目黒",
                        "regularHoliday":"年中無休",
                        "openingHour":"7時-22時",
                        "numberOfSeat":300,
                        "birthplace":"シアトル"
                        }
                        """));
    }

    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void 存在しない場所を指定した時に空文字が返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes?place=渋谷"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        []
                        """));
    }

    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void 存在しないIDを指定した時に例外404エラーが返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/8"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("こちらの情報は存在しません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/cafes/8"));
    }

    //Create処理の結合テスト
    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void カフェ情報が登録できていること() throws Exception {
        String newCafe = """
                {
                    "name":"New Name",
                    "place":"New Place",
                    "regularHoliday":"New RegularHoliday",
                    "openingHour":"New OpeningHour",
                    "numberOfSeat":1,
                    "birthplace":"New Birthplace"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/cafes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCafe))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message":"カフェ情報が登録されました"
                        }
                        """));
    }

    //Update処理の結合テスト
    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void カフェ情報が更新できること() throws Exception {
        String updatedCafe = """
                {
                    "name":"Updated Name",
                    "place":"Updated Place",
                    "regularHoliday":"Updated RegularHoliday",
                    "openingHour":"Updated OpeningHour",
                    "numberOfSeat":1,
                    "birthplace":"Updated Birthplace"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/cafes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCafe))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message":"カフェ情報が更新されました"
                        }
                        """));

    }

    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void 存在しないカフェ情報を更新しようとした時に例外404エラーが返ってくること() throws Exception {
        String updatedCafe = """
                {
                    "name":"Updated Name",
                    "place":"Updated Place",
                    "regularHoliday":"Updated RegularHoliday",
                    "openingHour":"Updated OpeningHour",
                    "numberOfSeat":1,
                    "birthplace":"Updated Birthplace"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/cafes/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCafe))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("カフェ情報が見つかりません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/cafes/8"));
    }

    //Delete処理の結合テスト
    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void カフェ情報が削除されていること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cafes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message":"カフェ情報が削除されました"
                        }
                        """));
    }

    @Test
    @DataSet(value = "datasets/cafes.yml")
    @Transactional
    void 存在しないIDのカフェ情報を削除しようとした時に例外404エラーが返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cafes/8"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("カフェ情報が見つかりません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/cafes/8"));
    }
}
