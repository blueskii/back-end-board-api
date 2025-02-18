package com.mycompany.backendservice;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

//Lombok의 @Slf4j는 Gralde Test에는 출력되지 않고, JUnit Test에만 출력됨(사용하지 말것)
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class BackEndServiceApplicationTests {
	private MockMvc mockMvc;
	private static String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXR5IjoiUk9MRV9VU0VSIiwiZXhwIjoxNzQxNzM1MDUyfQ.q2FF4WYGliDZrXmUe4uOcblglunX0-sZjorSgpfibB8";
	
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation)) 
			.apply(SecurityMockMvcConfigurers.springSecurity()) // Spring Security 통합
			.build();
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@Transactional
	void boardList() throws Exception {
		mockMvc.perform(get("/board/list")
			.param("pageNo", "1")
		)
		.andExpectAll(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(document("board/list", 
			preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
			queryParameters(
				parameterWithName("pageNo").description("페이지 번호")
			),
			responseFields(
	            fieldWithPath("pager.totalRows").description("페이징 대상이 되는 전체 행수"),
	            fieldWithPath("pager.totalPageNo").description("전체 페이지수"),
	            fieldWithPath("pager.totalGroupNo").description("전체 그룹수"),
	            fieldWithPath("pager.startPageNo").description("현재 그룹의 시작 페이지 번호"),
	            fieldWithPath("pager.endPageNo").description("현재 그룹의 끝 페이지 번호"),
	            fieldWithPath("pager.pageArray").description("현재 그룹의 페이지 번호 배열"),
	            fieldWithPath("pager.pageNo").description("현재 페이지 번호"),
	            fieldWithPath("pager.pagesPerGroup").description("그룹당 페이지수"),
	            fieldWithPath("pager.groupNo").description("현재 그룹의 번호"),
	            fieldWithPath("pager.rowsPerPage").description("패이지당 행수"),
	            fieldWithPath("pager.startRowNo").description("현재 페이지의 시작 행의 번호, 1부터 시작"),
	            fieldWithPath("pager.startRowIndex").description("현재 페이지의 시작 행의 인덱스, 0부터 시작"),
	            fieldWithPath("pager.endRowNo").description("현재 페이지의 끝 행의 순번, 1부터 시작"),
	            fieldWithPath("pager.endRowIndex").description("현재 페이지의 끝 행의 인덱스, 0부터 시작"),
	            
	            fieldWithPath("boards[].bno").description("게시물 번호"),
	            fieldWithPath("boards[].btitle").description("게시물 제목"),
	            fieldWithPath("boards[].bcontent").description("게시물 내용"),
	            fieldWithPath("boards[].bwriter").description("게시물 작성자").optional(),  // optional()로 null 필드 허용
	            fieldWithPath("boards[].bdate").description("게시물 작성 일자").optional(),
	            fieldWithPath("boards[].bhitcount").description("게시물 조회수").optional(),
	            fieldWithPath("boards[].battach").description("게시물 조회수").optional(),
	            fieldWithPath("boards[].battachoname").description("첨부 파일의 실제 이름").optional(),
	            fieldWithPath("boards[].battachsname").description("첨부 파일이 서버에 저장된 이름").optional(),
	            fieldWithPath("boards[].battachtype").description("첨부 파일의 타입").optional(),
	            fieldWithPath("boards[].battachdata").description("첨부 파일 데이터").optional()
	        ))); 
	}
	
	@Test
	@Transactional
	void boardCreate() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile(
	        "battach", 					// 요청 파라미터 이름
	        "test-file.txt", 			// 파일 원래 이름
	        MediaType.TEXT_PLAIN_VALUE, // 파일 MIME 타입
	        "파일 내용입니다.".getBytes(StandardCharsets.UTF_8) // 파일 데이터
	    );
		
		mockMvc.perform(multipart("/board/create")
			.file(mockFile) //파일 파트
			.param("btitle", "게시물 제목") //문자 파트
			.param("bcontent", "게시물 내용") //문자 파트
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.header("Authorization", "Bearer " + accessToken)
		)
		.andExpectAll(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(document("board/create", 
			preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestParts(
            	partWithName("btitle").description("문자파트: 게시물 제목").optional(), //optional()을 빼면 예외발생
            	partWithName("bcontent").description("문자파트: 게시물 내용").optional(),
              	partWithName("battach").description("파일파트: 첨부 파일").optional()
			),
			responseFields(
	            fieldWithPath("bno").description("게시물 번호"),
	            fieldWithPath("btitle").description("게시물 제목"),
	            fieldWithPath("bcontent").description("게시물 내용"),
	            fieldWithPath("bwriter").description("게시물 작성자").optional(),  // optional()로 null 필드 허용
	            fieldWithPath("bdate").description("게시물 작성 일자").optional(),
	            fieldWithPath("bhitcount").description("게시물 조회수").optional(),
	            fieldWithPath("battach").description("게시물 조회수").optional(),
	            fieldWithPath("battachoname").description("첨부 파일의 실제 이름").optional(),
	            fieldWithPath("battachsname").description("첨부 파일이 서버에 저장된 이름").optional(),
	            fieldWithPath("battachtype").description("첨부 파일의 타입").optional(),
	            fieldWithPath("battachdata").description("첨부 파일 데이터").optional()
	        )));
	}
	
	@Test
	@Transactional
	void boardRead() throws Exception {		
		mockMvc.perform(get("/board/read/{bno}", 10500)
			.header("Authorization", "Bearer " + accessToken)
		)
		.andExpectAll(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(document("board/read", 
			preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
            	parameterWithName("bno").description("게시물 번호")
			),
			responseFields(
	            fieldWithPath("bno").description("게시물 번호"),
	            fieldWithPath("btitle").description("게시물 제목"),
	            fieldWithPath("bcontent").description("게시물 내용"),
	            fieldWithPath("bwriter").description("게시물 작성자").optional(),  // optional()로 null 필드 허용
	            fieldWithPath("bdate").description("게시물 작성 일자").optional(),
	            fieldWithPath("bhitcount").description("게시물 조회수").optional(),
	            fieldWithPath("battach").description("게시물 조회수").optional(),
	            fieldWithPath("battachoname").description("첨부 파일의 실제 이름").optional(),
	            fieldWithPath("battachsname").description("첨부 파일이 서버에 저장된 이름").optional(),
	            fieldWithPath("battachtype").description("첨부 파일의 타입").optional(),
	            fieldWithPath("battachdata").description("첨부 파일 데이터").optional()
	        )));
	}	
	
	@Test
	@Transactional
	void boardUpdate() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile(
	        "battach", 					// 요청 파라미터 이름
	        "test-file.txt", 			// 파일 원래 이름
	        MediaType.TEXT_PLAIN_VALUE, // 파일 MIME 타입
	        "파일 내용입니다.".getBytes(StandardCharsets.UTF_8) // 파일 데이터
	    );
		
		mockMvc.perform(multipart("/board/update") //put 방식으로 하면 파일파트에서 에러
			.file(mockFile) 				//파일 파트
			.param("bno", "10500") 			//문자 파트
			.param("btitle", "게시물 제목") 	//문자 파트
			.param("bcontent", "게시물 내용") 	//문자 파트
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.header("Authorization", "Bearer " + accessToken)
		)
		.andExpectAll(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(document("board/update", 
			preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestParts(
            	partWithName("bno").description("문자파트: 게시물 번호").optional(),
            	partWithName("btitle").description("문자파트: 게시물 제목").optional(),
            	partWithName("bcontent").description("문자파트: 게시물 내용").optional(),
              	partWithName("battach").description("파일파트: 첨부 파일").optional()
			),
			responseFields(
	            fieldWithPath("bno").description("게시물 번호"),
	            fieldWithPath("btitle").description("게시물 제목"),
	            fieldWithPath("bcontent").description("게시물 내용"),
	            fieldWithPath("bwriter").description("게시물 작성자").optional(), 
	            fieldWithPath("bdate").description("게시물 작성 일자").optional(),
	            fieldWithPath("bhitcount").description("게시물 조회수").optional(),
	            fieldWithPath("battach").description("게시물 조회수").optional(),
	            fieldWithPath("battachoname").description("첨부 파일의 실제 이름").optional(),
	            fieldWithPath("battachsname").description("첨부 파일이 서버에 저장된 이름").optional(),
	            fieldWithPath("battachtype").description("첨부 파일의 타입").optional(),
	            fieldWithPath("battachdata").description("첨부 파일 데이터").optional()
	        )));
	}	
	
	@Test
	@Transactional
	void boardDelete() throws Exception {		
		mockMvc.perform(delete("/board/delete/{bno}", 10400)
			.header("Authorization", "Bearer " + accessToken)
		)
		.andExpectAll(status().isOk())
		.andDo(document("board/delete", 
			preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
            	parameterWithName("bno").description("게시물 번호")
			)));
	}	
	
	@Test
	@Transactional
	void boardBattach() throws Exception {		
		mockMvc.perform(get("/board/battach/{bno}", 10400)
			.header("Authorization", "Bearer " + accessToken)
		)
		.andExpectAll(status().isOk())
		.andDo(document("board/battach", 
			preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
            	parameterWithName("bno").description("게시물 번호")
			),
            responseHeaders(
                headerWithName("Content-Disposition").description("파일 다운로드 시 파일 이름"),
                headerWithName("Content-Type").description("응답의 콘텐츠 유형")
            )));
	}		
}






