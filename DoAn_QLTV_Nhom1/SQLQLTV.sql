CREATE DATABASE QL_ThuVien
GO

USE QL_ThuVien
--------------------Tạo bảng Sách--------------------------
CREATE TABLE Sach
(
	HinhAnh varchar(max),
	MaSach varchar(10) not null primary key,
	TenSach nvarchar(50),
	MoTa nvarchar(800),
	MaTL varchar(10) not null,
	MaTG varchar(10) not null,
	MaNXB varchar(10) not null,
	SoLuong int,
	NamXB int not null
);

--------------------Tạo bảng Tác giả-----------------------
CREATE TABLE TacGia
(
	 MaTG varchar(10) not null primary key,
	 TenTG nvarchar(50) not null,
	 GioiTinh nvarchar(10),
	 TieuSu nvarchar(500),
	 NgaySinh date,
	 QueQuan nvarchar(60)
);

--------------------Tạo bảng Thể loại-----------------------
CREATE TABLE TheLoai
(
	MaTL varchar(10) not null primary key,
	TenTL nvarchar (50) not null
);

--------------------Tạo bảng Nhà xuất bản-----------------------
CREATE TABLE NXB
(
	MaNXB varchar(10) not null primary key,
	TenNXB nvarchar(20) not null,
	DiaChi nvarchar(50) not null,
	Email nvarchar(50) not null,
	SoDT varchar(10) not null
);

--------------------Tạo bảng Tài khoản-----------------------
CREATE TABLE TaiKhoan
(
	MaNV varchar(10) primary key,
	Matkhau varchar(40) not null,
	Quyen int
);

--------------------Tạo bảng Độc giả-----------------------
CREATE TABLE DocGia
(
	MaDG varchar(10) not null primary key,
	TenDG nvarchar(50) not null,
	GioiTinh nvarchar(10),
	NgaySinh date not null,
	DiaChi nvarchar(20) not null,
	SoDT varchar(10) not null,	
	SoThe varchar(10) not null,
	TinhTrang nvarchar(50)
 );

 --------------------Tạo bảng Thẻ thư viện-----------------------
 CREATE TABLE TheThuVien
(
	SoThe varchar(10) not null primary key,
	NgayBD date not null,
	NgayKT date not null,
	TinhTrang nvarchar(30)
);

--------------------Tạo bảng Nhân viên-----------------------
CREATE TABLE NhanVien
(
	HinhAnh varchar(Max),
	MaNV varchar(10) not null primary key,
	TenNV nvarchar(50) not null,
	GioiTinh nvarchar(10),
	NgaySinh date not null,
	DiaChi nvarchar(60),
	NgayVL date,
	SoDT varchar(11) not null,
	ChucVu nvarchar(20)
);

--------------------Tạo bảng Chi tiết mượn trả-----------------------
CREATE TABLE CTMuonTra
(
	 MaMT varchar(10),
	 MaSach varchar(10) ,
	 CONSTRAINT Datra CHECK (NgayTra <> null),
	 NgayTra date null,
	 SoLuong int,
	 GhiChu nvarchar(20),
	 CONSTRAINT pk_department PRIMARY KEY (MaMT,MaSach)
);

--------------------Tạo bảng Mượn trả-----------------------
CREATE TABLE MuonTra
(
	MaMT varchar(10) not null primary key,
	SoThe varchar(10) not null,
	MaNV varchar(10) not null,
	NgayMuon date not null,
	NgayHenTra date,
	TinhTrang nvarchar(20)

);

--------------------Tạo bảng Danh sách quá hạn-----------------------
CREATE TABLE DSQuaHan
(
		MaMT varchar(10),
		SoNgayQH int,
		ngayQuaHan date,
		TrangThai nvarchar(50)
		CONSTRAINT pk_dsqh PRIMARY KEY (MaMT)
);

--------------------Tạo bảng Danh sách cấm-----------------------
CREATE TABLE DSCam
(
	MaDG varchar(10) primary key,
	NgayCam date,
	NgayMo date 
);

-----Thêm khóa ngoại cho các bảng--------
alter table Sach
	with nocheck add constraint fk_sach
	foreign key (MaTG) references TacGia(MaTG),
	foreign key (MaTl) references TheLoai(MaTL),
	foreign key (MaNXB) references NXB(MaNXB);

alter table DocGia
	with nocheck add constraint fk_dg
	foreign key (SoThe) references TheThuVien(SoThe);

alter table MuonTra
	with nocheck add constraint fk_mt
	foreign key (SoThe) references TheThuVien(SoThe),
	foreign key (MaNV) references NhanVien(MaNV);

alter table CTMuonTra
	with nocheck add constraint fk_ct
	foreign key (MaMT) references MuonTra(MaMT),
	foreign key (MaSach) references Sach(MaSach);

alter table TaiKhoan
	add constraint fk_nv_tk
	foreign key(MaNV) references NhanVien(Manv);

ALTER TABLE DSQuaHan
ADD CONSTRAINT FK_dsqh_mt
FOREIGN KEY (MaMT)
REFERENCES MuonTra(MaMT);

alter table DSCam
	add constraint fk_dsc_dg
	foreign key(MaDG) references DocGia(MaDG);

---------------Thêm data Thể loại------------
INSERT INTO TheLoai
VALUES 
(N'TL001', N'Truyện thơ'),
(N'TL002', N'Tiểu thuyết'),
(N'TL003', N'Truyện dài'),
(N'TL004', N'Truyện ngắn')

--------------Thêm data Nhà xuất bản-----------
INSERT INTO NXB
VALUES
(N'NXB001', N'Trẻ', N'Hà Nội', 'nxbtre@gmail.com', '0902340980'),
(N'NXB002', N'Hồng Đức', N'Đà Nẵng', 'nxbhongduc@gmail.com', '0902390234'),
(N'NXB003', N'Kim Đồng', N'TPHCM', 'nxbkimdong@gmail.com', '0678440980'),
(N'NXB004', N'Văn Học', N'Hà Nội', 'nxbvanhoc@gmail.com', '0902334590'),
(N'NXB005', N'Hội Nhà Văn', N'Nghệ An', 'nxbhoinhavan@gmail.com', '0987651980'),
(N'NXB006', N'Giáo Dục VN', N'Hà Nội', 'nxbgiaoduc@gmail.com', '0382151980')

---------------Thêm data Tác giả--------------
INSERT INTO TacGia([MaTG], [TenTG], [GioiTinh], [NgaySinh], [TieuSu], [QueQuan])
VALUES 
(N'TG001', N'Nguyễn Du', N'Nam', CAST(N'1765-01-03' AS Date), N'Nguyễn Du là một nhà thơ, nhà văn hóa lớn thời Lê mạt Nguyễn sơ ở Việt Nam. Ông được người Việt kính trọng tôn xưng là "Đại thi hào dân tộc" và được UNESCO vinh danh là "Danh nhân văn hóa thế giới".', N'Hà Tĩnh'),
(N'TG002', N'Đoàn Giỏi', N'Nam',CAST(N'1925-05-17' AS Date), N'Đoàn Giỏi là một nhà văn Việt Nam, hội viên Hội Nhà văn Việt Nam từ năm 1957. Ông có những bút danh khác như: Nguyễn Hoài, Nguyễn Phú Lễ, Huyền Tư.', N'Mỹ Tho'),
(N'TG003', N'Tô Hoài', N'Nam',CAST(N'1920-09-27' AS Date), N'Tô Hoài là một nhà văn Việt Nam. Ông được nhà nước Việt Nam trao tặng Giải thưởng Hồ Chí Minh về Văn học – Nghệ thuật.', N'Hà Nội'),
(N'TG004', N'Xuân Diệu', N'Nam',CAST(N'1916-02-02' AS Date), N'Xuân Diệu còn được mệnh danh là "ông hoàng thơ tình".', N'Bình Định'),
(N'TG005', N'Nguyễn Minh Châu', N'Nam',CAST(N'1930-10-20' AS Date), N'Nguyễn Minh Châu là một nhà văn có ảnh hưởng quan trọng đối với văn học Việt Nam trong giai đoạn chiến tranh Việt Nam và thời kỳ đầu của đổi mới.', N'Nghệ An')

---------------Thêm data Sách-------------
INSERT INTO Sach
 VALUES 
('src\\IMAGES\\IMG_SACH\\SH001.jpg', N'SH001', N'Truyện Kiều', N'Truyện Kiều là một truyện thơ của đại thi hào Nguyễn Du. Đây được xem là truyện thơ nổi tiếng nhất và xét vào hàng kinh điển trong văn học Việt Nam, tác phẩm được viết bằng chữ Nôm, theo thể lục bát, gồm 3.254 câu.', N'TL001', N'TG001', N'NXB001', 20, 2010),
('src\\IMAGES\\IMG_SACH\\SH002.JPG',N'SH002', N'Kim, Vân, Kiều', N'Kim, Vân, Kiều là tiểu thuyết chương hồi của Thanh Tâm Tài nhân - Tác giả đời nhà Minh, Trung Quốc. Nguyễn Du lấy cảm hứng từ tác phẩm này viết nên "Truyện Kiều" - Áng văn bất hủ của văn chương Việt Nam.', N'TL001', N'TG001', N'NXB002', 5, 2012),
('src\\IMAGES\\IMG_SACH\\SH003.JPG',N'SH003', N'Đất rừng phương Nam', N'Đất rừng phương Nam là tiểu thuyết của nhà văn Đoàn Giỏi viết về cuộc đời phiêu bạt của cậu bé tên An. Bối cảnh của tiểu thuyết là miền Tây Nam Bộ, Việt Nam vào những năm 1930, sau khi thực dân Pháp quay trở lại xâm chiếm Nam Bộ.', N'TL002', N'TG002', N'NXB003', 9, 2022),
('src\\IMAGES\\IMG_SACH\\SH004.JPG',N'SH004', N'Cá bống mú', N'Truyện ngắn Cá bống mú ra đời dưới ngòi bút tài hoa của nhà văn Đoàn Giỏi. Đây là một trong những tác phẩm cho thấy cuộc sống của người dân Nam Bộ khi phải chịu khổ cực, bị đàn áp, bóc lột dã man dưới tay bọn thực dân và cường hào, địa chủ.', N'TL002', N'TG002', N'NXB004', 11, 2022),
('src\\IMAGES\\IMG_SACH\\SH005.JPG',N'SH005', N'Hoa Hướng Dương', N'Truyện đã khắc họa vùng đất, tâm hồn người miền Tây thời kỳ trước Cách mạng tháng Tám vô cùng ấn tượng. Hành trình của người mẹ cũng là người chiến sĩ can trường sinh con trong muôn ngàn nguy biến, lồng ghép tuyệt vời với văn hóa sông nước Nam bộ hiện lên sống động, lôi cuốn như những thước phim hay nhất về chiến tranh và sức sống con người.', N'TL003', N'TG002', N'NXB003', 14, 2022),
('src\\IMAGES\\IMG_SACH\\SH006.JPG',N'SH006', N'Dế Mèn Phiêu Lưu Ký', N' "Dế Mèn phiêu lưu kí" là một trong những sáng tác tâm đắc nhất của nhà văn Tô Hoài. Tác phẩm đã được tái bản nhiều lần và được dịch ra hơn 20 thứ tiếng trên thế giới và luôn được các thế hệ độc giả nhỏ tuổi đón nhận. Tác phẩm đã được xuất bản với nhiều hình thức khác nhau.', N'TL002', N'TG003', N'NXB003', 18, 2022),
('src\\IMAGES\\IMG_SACH\\SH007.JPG',N'SH007', N'Kim Đồng', N'Cuốn sách là câu chuyện cảm động về một trong những đội viên đầu tiên của Đội Thiếu niên Tiền phong Hồ Chí Minh - anh Kim Đồng.', N'TL004', N'TG003', N'NXB003', 11, 2022),
('src\\IMAGES\\IMG_SACH\\SH008.JPG',N'SH008', N'Chiều Chiều', N'Chiều Chiều là con sông dài, qua bao nhiêu ghềnh thác vẫn tiếp tục xuôi dòng hồi ức 80 năm của Tô Hoài, mà vẫn chưa hò hẹn ngày xuống đồng bằng hay đổ ra biển cả. Vậy Chiều Chiều đã mang lại cho người đọc những cảm thụ gì mới? So với những tự truyện trước, Chiều Chiều nặng phần phê phán thời đại mà tác giả đã trải qua; ở các tác phẩm trước, Tô Hoài thường bao che, bào chữa, như là một hồi ký bao cấp.', N'TL002', N'TG003', N'NXB005', 6, 2022),
('src\\IMAGES\\IMG_SACH\\SH009.JPG',N'SH009', N'Thơ Thơ', N'Thơ Thơ là tên một tập thơ được xuất bản năm 1938, là tập thơ đầu tay của xuân diệu, cùng với Gửi hương cho gió, cho đến nay vẫn là hai tác phẩm nổi bật nhất của Xuân Diệu.', N'TL001', N'TG004', N'NXB005', 4, 2022),
('src\\IMAGES\\IMG_SACH\\SH010.JPG',N'SH010', N'Những ngày lưu lạc', N'“Những ngày lưu lạc” nằm trong Bộ ba tiểu thuyết thiếu nhi Từ giã tuổi thơ - Những ngày lưu lạc - Đảo đá kỳ lạ của Nguyễn Minh Châu. Tác phẩm là câu chuyện về cuộc đời của chú bé Nến, bố mẹ bị giặc giết hại, chú lưu lạc khắp nơi.', N'TL002', N'TG005', N'NXB004', 8, 2022),
('src\\IMAGES\\IMG_SACH\\SH011.JPG',N'SH011', N'Đảo đá kì lạ', N'Sau Những ngày lưu lạc, chú bé Nến tình cờ được đưa đến Đảo đá kì lạ. Tại đây, cậu bé đã gặp lại người du kích đồng hương, biết giá trị của loại đá quý hiếm trên đảo và quyết tâm giữ gìn tài nguyên của đất nước của các chiến sĩ cách mạng bị giam cầm trên đảo.', N'TL002', N'TG005', N'NXB003', 13, 2022),
('src\\IMAGES\\IMG_SACH\\SH0012.JPG',N'SH012', N'Từ giã tuổi thơ', N'“Từ giã tuổi thơ” nằm trong Bộ ba tiểu thuyết thiếu nhi Từ giã tuổi thơ - Những ngày lưu lạc - Đảo đá kỳ lạ  của Nguyễn Minh Châu. Tác phẩm viết về cuộc đời của chú bé Nến, bố mẹ bị giặc giết hại, chú lưu lạc khắp nơi, về quê cũ, bị địch bắt đi phu, bắt ra đảo hoang… Nhưng trong hoàn cảnh nào chú vẫn giữ được  tình yêu quê hương đất nước và lòng căm thù giặc.', N'TL002', N'TG005', N'NXB004', 22, 2022)

---------------Thêm data Thẻ thư viện------------
INSERT INTO TheThuVien
VALUES
('ST001', CAST(N'2024-01-10' AS Date), CAST(N'2024-04-10' AS Date), null),
('ST002', CAST(N'2024-02-23' AS Date), CAST(N'2024-05-23' AS Date), null),
('ST003', CAST(N'2024-03-06' AS Date), CAST(N'2024-06-06' AS Date), null),
('ST004', CAST(N'2024-04-30' AS Date), CAST(N'2024-07-30' AS Date), null),
('ST005', CAST(N'2024-04-30' AS Date), CAST(N'2024-07-30' AS Date), null)

---------------Thêm data Độc giả------------
INSERT INTO DocGia
VALUES
('DG004', N'Nguyễn Gia Kiệt', N'Nam', CAST(N'2004-02-10' AS Date), N'TPHCM', '0901658923', 'ST001', N'Bình thường'),
('DG002', N'Lê Thị Hoàng', N'Nữ', CAST(N'2000-10-20' AS Date), N'Bến Tre', '0901690123', 'ST002', N'Bình thường'),
('DG003', N'Lê Thị Hoa', N'Nữ', CAST(N'2000-10-20' AS Date), N'Bến Tre', '0901690123', 'ST003', N'Bình thường')

---------------Thêm data Nhân viên------------
INSERT INTO NhanVien
VALUES
('src\\IMAGES\\IMG_NV\\NV001.png','admin',N'Võ Danh Dự',N'Nam','2003-02-25','HCM','2024-04-30','04924242',N'admin'),
('src\\IMAGES\\IMG_NV\\NV001.png','NV001',N'Võ Danh Dự',N'Nam','2003-02-25','HCM','2024-04-30','04924242',N'Quản lí'),
('src\\IMAGES\\IMG_NV\\NV002.png','NV002',N'Đặng Phước Đạt',N'Nam','2003-02-15','HCM','2024-04-29','0492434567',N'Nhân viên'),
('src\\IMAGES\\IMG_NV\\NV003.png','NV003',N'Nguyễn Quốc Dương',N'Nam','2003-06-23','HCM','2024-04-30','04924242',N'Nhân viên'),
('src\\IMAGES\\IMG_NV\\NV004.png','NV004',N'Trà Thị Thanh Trúc',N'Nữ','2003-10-06','HCM','2024-04-29','04924785',N'Nhân viên')






--------Tao tk admin-------------
INSERT INTO TaiKhoan
VALUES
('admin','123',0)
-----Xem thông tin các bảng--------


SELECT * FROM NXB
SELECT * FROM Sach
SELECT * FROM TacGia
SELECT * FROM TheLoai
SELECT * FROM DocGia
SELECT * FROM TheThuVien
SELECT * FROM DSCam





----------------------------------------------------------------VÕ DANH DỰ------------------------------------------------------------------------------------

-----------------------------PROCE_Thêm mượn trả---------------------------------------------------------------
CREATE PROCEDURE ThemMuonTra
    @SoThe VARCHAR(10),
    @MaNV VARCHAR(10),
    @NgayMuon DATE,
    @NgayHenTra DATE
AS
BEGIN
    DECLARE @MaMT VARCHAR(10);

    -- Tạo mã mượn trả dựa trên số thứ tự tăng dần
    DECLARE @MaxMaMT INT;
    SELECT @MaxMaMT = ISNULL(MAX(CAST(SUBSTRING(MaMT, 3, LEN(MaMT) - 2) AS INT)), 0) + 1
    FROM MuonTra
    WHERE LEFT(MaMT, 2) = 'MT';

    SET @MaMT = 'MT' + RIGHT('000' + CAST(@MaxMaMT AS VARCHAR(3)), 3);

    -- Thêm mượn trả vào bảng MuonTra
    INSERT INTO MuonTra (MaMT, SoThe, MaNV, NgayMuon, NgayHenTra, TinhTrang)
    VALUES (@MaMT, @SoThe, @MaNV, @NgayMuon, @NgayHenTra, N'Chưa trả');
END;

------------------------------------------PROCE _KIỂM TRA QUÁ HÁN----------------------------------------------------------------------------------------------------
CREATE PROCEDURE UpdateStatusAndAddToDSQuaHan
		@MaMT varchar(10),
		@NgayHenTra date
	AS
	BEGIN
		DECLARE @NgayHienTai date;
		SET @NgayHienTai = GETDATE();

		-- Kiểm tra điều kiện
		IF EXISTS (SELECT 1 FROM MuonTra WHERE MaMT = @MaMT AND NgayHenTra < @NgayHienTai AND TinhTrang = N'Chưa trả')
		BEGIN
			-- Kiểm tra và cập nhật tình trạng của MuonTra
			UPDATE MuonTra
			SET TinhTrang = N'Quá hạn'
			WHERE MaMT = @MaMT
			AND TinhTrang = N'Chưa trả';

			-- Cập nhật ghi chú của CTMuonTra
			UPDATE CTMuonTra
			SET GhiChu = N'Quá hạn'
			WHERE MaMT = @MaMT
			AND GhiChu = N'Chưa trả';

			-- Thêm vào DSQuaHan
			INSERT INTO DSQuaHan(MaMT, SoNgayQH, NgayQuaHan, TrangThai)
			VALUES (@MaMT, 0, @NgayHenTra, N'Chưa xử lí');
		END
END;

----------------------------------------------PROCE_ cập nhật số ngày quá hạn----------------------------------
	CREATE PROCEDURE CapNhatSoNgayQuaHan
    @MaMT varchar(10),
    @TinhTrang nvarchar(50),
    @NgayQuaHan date
AS
BEGIN	
    -- Kiểm tra điều kiện để xác định liệu có cần cập nhật không
    IF @TinhTrang = N'Chưa xử lí'
    BEGIN
        -- Tính số ngày quá hạn
        DECLARE @SoNgayQuaHan int;
        SET @SoNgayQuaHan = DATEDIFF(day, @NgayQuaHan, GETDATE());

        -- Cập nhật số ngày quá hạn vào bảng DSQuaHan
        UPDATE DSQuaHan
        SET SoNgayQH = @SoNgayQuaHan
        WHERE MaMT = @MaMT;
    END
END;

----------------------------------------------PROCEDURE_ Xử lí quá hạn----------------------------------
CREATE PROCEDURE UpdateQuaHanAndMuonTra @MaMT varchar(10)
AS
BEGIN
    BEGIN TRANSACTION;

    -- Cập nhật trạng thái của DSQuaHan
    UPDATE DSQuaHan
    SET TrangThai = N'Đã xử lí'
    WHERE MaMT = @MaMT;

    -- Cập nhật tình trạng của phiếu mượn (MuonTra) thành 'Đã trả'
    UPDATE MuonTra
    SET TinhTrang = N'Đã trả'
    WHERE MaMT = @MaMT;

    -- Cập nhật số lượng sách trong bảng Sach
    DECLARE @SoLuongMuon int;
    DECLARE @MaSach varchar(10);

    -- Lấy danh sách các mã sách và số lượng mượn
    DECLARE cur CURSOR FOR
    SELECT MaSach, SoLuong
    FROM CTMuonTra
    WHERE MaMT = @MaMT AND GhiChu = N'Quá hạn';

    OPEN cur;
    FETCH NEXT FROM cur INTO @MaSach, @SoLuongMuon;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- Cập nhật số lượng sách trong bảng Sach
        UPDATE Sach
        SET SoLuong = SoLuong + @SoLuongMuon
        WHERE MaSach = @MaSach;

        -- Cập nhật lại thông tin trả sách trong CTMuonTra
        UPDATE CTMuonTra
        SET NgayTra = GETDATE(), GhiChu = N'Đã trả'
        WHERE MaMT = @MaMT AND GhiChu = N'Quá hạn' AND MaSach = @MaSach;

        FETCH NEXT FROM cur INTO @MaSach, @SoLuongMuon;
    END;

    CLOSE cur;
    DEALLOCATE cur;

    COMMIT TRANSACTION;
END;

 ----------------------------------------------PROEDURE_Thêm vào ds cấm----------------------------------
 CREATE PROCEDURE UpdateBannedStatus
    @MaMT VARCHAR(10),
    @SoNgayCam INT
AS
BEGIN
    DECLARE @NgayHienTai DATE = GETDATE()
	DECLARE @maDG varchar(10) 
    DECLARE @NgayMo DATE = DATEADD(day, 5, @NgayHienTai)
	set @maDG =  (SELECT MaDG FROM DocGia WHERE SoThe = (Select SoThe from MuonTra where MaMT = @MaMT))

    -- Cập nhật tình trạng trong bảng độc giả
    UPDATE DocGia
    SET TinhTrang = N'Bị cấm'
    WHERE MaDG = @maDG
	
    -- Thêm độc giả vào DsCam
    INSERT INTO DsCam (MaDG, NgayCam, NgayMo)
    Values (@maDG, @NgayHienTai, @NgayMo)       
END;

----------------------------------------------TRIGGER XÓA_ cập nhật trạng thái----------------------------------
CREATE TRIGGER capNhatTinhTrangKhiXoaKhoiDsCam
ON DsCam
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @MaDG VARCHAR(10);

    SELECT @MaDG = MaDG FROM deleted;

    UPDATE DocGia
    SET TinhTrang = N'Bình thường'
    WHERE MaDG = @MaDG;
END;
-------------------------------------Proc xóa Mượn Trả-------------------------------------------------------------------
CREATE PROCEDURE Delete_MuonTra
    @MaMT VARCHAR(10)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION; -- Bắt đầu giao dịch

        -- Kiểm tra xem có dòng nào trong bảng dsQuahan chứa MaMT cần xóa hay không
        IF EXISTS (SELECT 1 FROM dsQuahan WHERE MaMT = @MaMT)
        BEGIN
            -- Nếu có, thực hiện xóa dòng đó
            DELETE FROM dsQuahan WHERE MaMT = @MaMT;
        END;

        -- Xóa dữ liệu từ bảng ctMuonTra
      
		 IF EXISTS (SELECT 1 FROM CTMuonTra WHERE MaMT = @MaMT)
        BEGIN
            -- Nếu có, thực hiện xóa dòng đó
              DELETE FROM ctMuonTra WHERE MaMT = @MaMT;
        END;

        -- Xóa dữ liệu từ bảng muonTra
        DELETE FROM muonTra WHERE MaMT = @MaMT;

        COMMIT TRANSACTION; -- Chấp nhận giao dịch nếu không có vấn đề

    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION; -- Quay lại trạng thái trước đó nếu có vấn đề

        -- Xử lý lỗi tại đây nếu cần thiết
        THROW;
    END CATCH
END;	

------------------------------Proc cập nhật chi tiết mượn trả--------------------------------
CREATE PROCEDURE UpdateMuonTraStatus @MaMT varchar(10)
AS
BEGIN
    -- Kiểm tra xem có bất kỳ dòng nào trong bảng CTMuonTra có ghiChu = 'Chưa trả' hay không
    IF NOT EXISTS (SELECT 1 FROM CTMuonTra WHERE MaMT = @MaMT)
    BEGIN
        -- Nếu không có dòng nào trong bảng CTMuonTra có MaMT tương ứng, không làm gì cả
        RETURN; -- Thoát khỏi stored procedure
    END;
    
    IF EXISTS (SELECT 1 FROM CTMuonTra WHERE MaMT = @MaMT AND GhiChu = N'Chưa trả')
    BEGIN
        -- Nếu có, cập nhật tinhTrang của bảng MuonTra thành 'Chưa trả' cho mã mượn trả tương ứng
        UPDATE MuonTra 
        SET TinhTrang = N'Chưa trả' 
        WHERE MaMT = @MaMT;
    END
    ELSE
    BEGIN
        -- Nếu không, cập nhật tinhTrang của bảng MuonTra thành 'Đã trả' cho mã mượn trả tương ứng
        UPDATE MuonTra 
        SET TinhTrang = N'Đã trả' 
        WHERE MaMT = @MaMT;
    END;
END;
---------------------------------------Proc lấy thông tin sách từ mã sách------------------------------------------------
CREATE PROCEDURE GetBookInfoByMaSach
    @MaSach VARCHAR(10)
AS
BEGIN
    SELECT
        Sach.HinhAnh,
        Sach.TenSach,
        TacGia.TenTG AS TenTacGia,
        TheLoai.TenTL AS TenTheLoai,
        Sach.NamXB,
        NXB.TenNXB,
        Sach.SoLuong
    FROM
        Sach
    INNER JOIN
        TacGia ON Sach.MaTG = TacGia.MaTG
    INNER JOIN
        TheLoai ON Sach.MaTL = TheLoai.MaTL
    INNER JOIN
        NXB ON Sach.MaNXB = NXB.MaNXB
    WHERE
        Sach.MaSach = @MaSach;
END;
--------------------------------------------Proc tìm sách theo tên--------------------------------------------------------------
CREATE PROCEDURE SearchBookByName
    @TenSach NVARCHAR(50)
AS
BEGIN
    SELECT * FROM Sach WHERE TenSach LIKE '%' + @TenSach + '%';
END;
-----------------------------------Proc tìm sách theo mã------------------------
CREATE PROCEDURE SearchBookByMasach
    @MaSach NVARCHAR(10)
AS
BEGIN
    SELECT * FROM Sach WHERE MaSach = @MaSach;
END;
-------------------------Proc thống kê phiếu mượn trong tuần-------------------

CREATE PROCEDURE ThongKePhieuMuonTrongTuan
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM MuonTra
    WHERE DATEPART(WEEK, NgayMuon) = DATEPART(WEEK, GETDATE());
END;	
--------------------------Proc thống kê phiếu mượn trong tuần chưa trả-----------------------
CREATE PROCEDURE ThongKePhieuMuonTrongTuanChuaTra
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM MuonTra
    WHERE DATEPART(WEEK, NgayMuon) = DATEPART(WEEK, GETDATE()) and TinhTrang = N'Chưa trả';
END;
-------------------------Proc thống kê phiếu mượn trong tuần đã trả----------------------
CREATE PROCEDURE ThongKePhieuMuonTrongTuanDaTra
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM MuonTra
    WHERE DATEPART(WEEK, NgayMuon) = DATEPART(WEEK, GETDATE()) and TinhTrang = N'Đã trả';
END;
--------------------------------Proc thống kê phiếu mượn trong tuần quá hạn--------------------
CREATE PROCEDURE ThongKePhieuMuonTrongTuanQuaHan
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM DSQuaHan
    WHERE DATEPART(WEEK, ngayQuaHan) = DATEPART(WEEK, GETDATE());
END;
------------------------------------------------------------------------------------------------
CREATE PROCEDURE CountMuonTraByWeekday
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @CurrentWeekNumber INT;
    SET @CurrentWeekNumber = DATEPART(WEEK, GETDATE());

    SELECT
        AllWeekdays.Weekday,
        ISNULL(COUNT(MuonTra.NgayMuon), 0) AS SoLuongMuon
    FROM (
        VALUES ('Monday'), ('Tuesday'), ('Wednesday'), ('Thursday'), ('Friday'), ('Saturday'), ('Sunday')
    ) AS AllWeekdays (Weekday)
    LEFT JOIN MuonTra ON DATENAME(WEEKDAY, MuonTra.NgayMuon) = AllWeekdays.Weekday
        AND DATEPART(WEEK, MuonTra.NgayMuon) = @CurrentWeekNumber
    GROUP BY
        AllWeekdays.Weekday
    ORDER BY
        CASE AllWeekdays.Weekday
            WHEN 'Monday' THEN 1
            WHEN 'Tuesday' THEN 2
            WHEN 'Wednesday' THEN 3
            WHEN 'Thursday' THEN 4
            WHEN 'Friday' THEN 5
            WHEN 'Saturday' THEN 6
            WHEN 'Sunday' THEN 7
        END;
END;



---------------------------------------------------------------------ĐẶNG PHƯỚC ĐẠT-------------------------------------------------------------------------
----Proc thêm thẻ thư viện------------------
CREATE PROCEDURE themTheThuVien
AS
BEGIN
    DECLARE @SoThe varchar(10);
    DECLARE @NgayBD date;
    DECLARE @NgayKT date;
	DECLARE @tinhtrang nvarchar(30);
	DECLARE @MaxSoThe INT;

    SET @NgayBD = GETDATE();
    SET @NgayKT = DATEADD(MONTH, 3, @NgayBD);
    
    SELECT @MaxSoThe = ISNULL(MAX(SUBSTRING(SoThe, 3, LEN(SoThe) - 2)), 0) + 1
    FROM TheThuVien
    WHERE LEFT(SoThe, 2) = 'ST';

    SET @SoThe = 'ST' + RIGHT('000' + CAST(@MaxSoThe AS VARCHAR(3)), 3);

    INSERT INTO TheThuVien (SoThe, NgayBD, NgayKT, TinhTrang)
    VALUES (@SoThe, @NgayBD, @NgayKT, @tinhtrang);
END;

-----Proc kiểm tra ngày quá hạn-------
CREATE PROCEDURE kiemTraTinhTrangThe
    @SoThe VARCHAR(10),
	@ngayBD DATE,
    @ngayKT DATE,
    @TinhTrang NVARCHAR(30)
AS
BEGIN
    IF @ngayKT < GETDATE()
    BEGIN
        SET @TinhTrang = N'Hết hạn';
    END
    ELSE
    BEGIN
        SET @TinhTrang = N'Còn hạn';
    END

    UPDATE TheThuVien
    SET TinhTrang = @TinhTrang
    WHERE SoThe = @SoThe;
END

---Proc tự dộng xóa độc giả khi tới ngày mở------
CREATE PROCEDURE xoaDocGiaTrongDsCam
    @MaDG VARCHAR(10)
AS
BEGIN

    DECLARE @NgayHienTai DATE = GETDATE();
	if(@NgayHienTai >= (Select NgayMo from DSCam where MaDG = @MaDG))
        -- Xóa độc giả trong bảng DsCam
		BEGIN
			DELETE FROM DsCam where MaDG = @MaDG;
		END;  
END;

-----Proc thêm độc giả--------------
Create proc themDocGia
(
	@tendocgia nvarchar(50),
	@gioitinh nvarchar(10),
	@ngaysinh date,
	@diachi nvarchar(20), 
	@sdt varchar(10),
	@sothe varchar(10)

)
As
Begin
	DECLARE @madocgia varchar(10);
    DECLARE @MaxMaDG INT;
	DECLARE @tinhtrang nvarchar(50) = N'Bình thường'
	
    SELECT @MaxMaDG = ISNULL(MAX(SUBSTRING(MaDG, 3, LEN(MaDG) - 2)), 0) + 1
    FROM DocGia
    WHERE LEFT(MaDG, 2) = 'DG';

    SET @madocgia = 'DG' + RIGHT('000' + CAST(@MaxMaDG AS VARCHAR(3)), 3);
		Insert into docgia
			values(@madocgia, @tendocgia, @gioitinh, @ngaysinh ,@diachi, @sdt, @sothe, @tinhtrang);
End;

-------Proc kiểm tra số thẻ không tồn tại trong độc giả--------------------------------------
CREATE PROCEDURE DanhSachSoTheKhongTonTaiTrongDocGia
AS
BEGIN
    SELECT SoThe
    FROM TheThuVien
    WHERE SoThe NOT IN (
        SELECT SoThe
        FROM DocGia
    )
END;

-------------------------------------------------------------------TRÀ THỊ THANH TRÚC-------------------------------------------------------------------------
-----Proc cập nhật sách--------------
CREATE PROCEDURE CapNhatSach
	@hinhanh VARCHAR(100),
    @masach VARCHAR(10),
    @tensach NVARCHAR(50),
    @mota NVARCHAR(800),
    @matl VARCHAR(10),
    @matg VARCHAR(10),
    @manxb VARCHAR(10),
    @soluong INT,
    @namxb INT
AS
BEGIN
    UPDATE Sach
    SET HinhAnh=@hinhanh,
		TenSach = @tensach,
        MoTa = @mota,
        MaTL = @matl,
        MaTG = @matg,
        MaNXB = @manxb,
        SoLuong = @soluong,
        NamXB = @namxb
    WHERE MaSach = @masach;
END;

-----Proc thêm tác giả--------------
CREATE PROCEDURE themTG(
    @tentg NVARCHAR(50),
    @gioiTinh NVARCHAR(100),
    @tieuSu NVARCHAR(800),
    @ngaySinh DATE,
    @queQuan NVARCHAR(100))
AS
BEGIN
    DECLARE @ma VARCHAR(10);
    
    DECLARE @Max INT;
    SELECT @Max = ISNULL(MAX(CAST(SUBSTRING(MaTG, 3, LEN(MaTG) - 2) AS INT)), 0) + 1
    FROM TacGia
    WHERE LEFT(MaTG, 2) = 'TG';

    SET @ma = 'TG' + RIGHT('000' + CAST(@Max AS VARCHAR(3)), 3);

    INSERT INTO TacGia([MaTG], [TenTG], [GioiTinh], [TieuSu], [NgaySinh], [QueQuan])
    VALUES (@ma,@tentg,@gioiTinh,@tieuSu,@ngaySinh,@queQuan);
END;

-----Proc thêm thể loại--------------
CREATE PROCEDURE themTL(
    @tentl NVARCHAR(50))
AS
BEGIN
    DECLARE @ma VARCHAR(10);
    
    DECLARE @Max INT;
    SELECT @Max = ISNULL(MAX(CAST(SUBSTRING(MaTL, 3, LEN(MaTL) - 2) AS INT)), 0) + 1
    FROM TheLoai
    WHERE LEFT(MaTL, 2) = 'TL';

    SET @ma = 'TL' + RIGHT('000' + CAST(@Max AS VARCHAR(3)), 3);

    INSERT INTO TheLoai([MaTL], [TenTL])
    VALUES (@ma,@tentl);
END;

-----Proc thêm nhà xuất bản--------------
CREATE PROCEDURE themNXB(
	@tenNXB nvarchar(20) ,
	@diaChi nvarchar(50) ,
	@email nvarchar(50) ,
	@soDT varchar(10) )
AS
BEGIN
    DECLARE @ma VARCHAR(10);
    
    DECLARE @Max INT;
    SELECT @Max = ISNULL(MAX(CAST(SUBSTRING(MaNXB, 4, LEN(MaNXB) - 3) AS INT)), 0) + 1
    FROM NXB
    WHERE LEFT(MaNXB, 3) = 'NXB';

    SET @ma = 'NXB' + RIGHT('000' + CAST(@Max AS VARCHAR(3)), 3);

    INSERT INTO NXB([MaNXB], [TenNXB], [DiaChi], [Email], [SoDT])
    VALUES (@ma,@tenNXB,@diaChi,@email,@soDT);
END;
-------------------------

CREATE PROCEDURE themSach
	@anh varchar(Max),
    @tensach NVARCHAR(50),
    @mota NVARCHAR(800),
    @matl VARCHAR(10),
    @matg VARCHAR(10),
    @manxb VARCHAR(10),
    @soluong INT,
    @namxb INT
AS
BEGIN
    DECLARE @masach VARCHAR(10);
    
    DECLARE @MaxMaSach INT;
    SELECT @MaxMaSach = ISNULL(MAX(CAST(SUBSTRING(MaSach, 3, LEN(MaSach) - 2) AS INT)), 0) + 1
    FROM Sach
    WHERE LEFT(MaSach, 2) = 'SH';

    SET @masach = 'SH' + RIGHT('000' + CAST(@MaxMaSach AS VARCHAR(3)), 3);

    INSERT INTO Sach ([HinhAnh],[MaSach], [TenSach], [MoTa], [MaTL], [MaTG], [MaNXB], [SoLuong], [NamXB])
    VALUES (@anh,@masach, @tensach, @mota, @matl, @matg, @manxb, @soluong, @namxb);
END;





