CREATE TABLE board_list
(
    `blno`   INT  		AUTO_INCREMENT COMMENT 'blno', 
    `bname`  VARCHAR(45)   NOT NULL        COMMENT 'bname', 
    PRIMARY KEY (blno)
);



CREATE TABLE members
(
    `mno`        INT 			AUTO_INCREMENT COMMENT 'mno', 
    `id`         VARCHAR(45)    NOT NULL        COMMENT 'id', 
    `email`      VARCHAR(45)    NOT NULL        COMMENT 'email', 
    `pwd`        VARCHAR(45)    NOT NULL        COMMENT 'pwd', 
    `mname`      VARCHAR(45)    NOT NULL        COMMENT 'mname', 
    `cre_date`  DATETIME	   NOT NULL        COMMENT 'cre_date',
    PRIMARY KEY (mno)
);


CREATE TABLE board
(
    `cno`       INT   AUTO_INCREMENT COMMENT 'cno', 
    `blno`      INT            NOT NULL        COMMENT 'blno', 
    `title`     VARCHAR(45)    NOT NULL        COMMENT 'title', 
    `contents`  VARCHAR(1000)    NOT NULL        COMMENT 'contents',
    `img`  VARCHAR(500)    NULL        COMMENT 'img', 
    `id`        VARCHAR(45)    NOT NULL        COMMENT 'id', 
    `reg_date`  TIMESTAMP DEFAULT NOW()	NOT NULL,
    `view_count` INT 			NOT NULL,
    PRIMARY KEY (cno)
);



SELECT cno, title, id, reg_date, view_count FROM Board ORDER BY cno ASC;

ALTER TABLE members
         ADD CONSTRAINT members_id_UNIQUE UNIQUE(id);

ALTER TABLE board
    ADD CONSTRAINT FK_board_id_members_id FOREIGN KEY (id)
        REFERENCES members (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE board
    ADD CONSTRAINT FK_board_blno_board_list_blno FOREIGN KEY (blno)
        REFERENCES board_list (blno) ON DELETE CASCADE ON UPDATE CASCADE;
        
ALTER TABLE members
   MODIFY COLUMN mno INTEGER NOT NULL AUTO_INCREMENT
    COMMENT '회원일련번호';
    
INSERT INTO members(id,email,pwd,mname,cre_date)
VALUES ('s1','s1@test.com','1111','아이유',NOW());
INSERT INTO members(id, email,pwd,mname,cre_date)
VALUES ('s2', 's2@test.com','1111','트와이스사나',NOW());
INSERT INTO members(id, email,pwd,mname,cre_date)
VALUES ('s3', 's3@test.com','1111','블랙핑크제니',NOW());
INSERT INTO members(id, email,pwd,mname,cre_date)
VALUES ('s4', 's4@test.com','1111','차은우',NOW());
INSERT INTO members(id, email,pwd,mname,cre_date)
VALUES ('s5', 's5@test.com','1111','박보검',NOW());



INSERT INTO board_list(bname)
VALUES ('공지');

INSERT INTO board_list(bname)
VALUES ('자유게시판');

INSERT INTO board_list(bname)
VALUES ('자료공유');

INSERT INTO board_list(bname)
VALUES ('중고거래');


INSERT INTO board(blno, title, contents, id, view_count)
VALUES (1,'공지사항','등업신청','s1',1);

INSERT INTO board(blno, title, contents, id, view_count)
VALUES (2,'자유게시판','등업신청','s1',2);

INSERT INTO board(blno, title, contents, id, view_count)
VALUES (3,'자료공유','등업신청','s1',2);

INSERT INTO board(blno, title, contents, id, view_count)
VALUES (4,'중고거래','등업신청','s1',2);

-------------------------------------

CREATE TABLE comment
(
    `comno`        INT          AUTO_INCREMENT COMMENT 'comment number', 
	`cno`    	   INT    		NOT NULL	 COMMENT 'contennts number', 
	`id`        VARCHAR(45)    NOT NULL        COMMENT 'id', 
    `comments`   VARCHAR(200)    NOT NULL        COMMENT 'comment', 
    `comreg_date`  TIMESTAMP      default now()    not null   COMMENT 'comment regdate',
    PRIMARY KEY (comno)
);


ALTER TABLE comment
    ADD CONSTRAINT FK_comment_cno_board_cno FOREIGN KEY (cno)
        REFERENCES board (cno) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE comment
    ADD CONSTRAINT FK_comment_id_members_id FOREIGN KEY (id)
        REFERENCES members (id) ON DELETE CASCADE ON UPDATE CASCADE;        
        
SELECT id, comments, comreg_date FROM comment ORDER BY comreg_date ASC;

----------------------------
INSERT INTO comment(comno, cno, id, comments, comreg_date)
VALUES (1,'1','s1','공지사항 코멘트테스트입니다1',NOW());
INSERT INTO comment(comno, cno, id, comments, comreg_date)
VALUES (2,'1','s1','공지사항 코멘트테스트입니다2',NOW());
INSERT INTO comment(comno, cno, id, comments, comreg_date)
VALUES (3,'2','s1','자유게시판 코멘트테스트입니다',NOW());