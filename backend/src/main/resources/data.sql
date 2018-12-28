INSERT INTO m_roles (roleid, roledesc) VALUES (1, N'ADMIN')
INSERT INTO m_roles (roleid, roledesc) VALUES (2, N'USER')
INSERT INTO m_roles (roleid, roledesc) VALUES (3, N'SOUMU')

INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (1, 1, N'プロジェクト管理者')
INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (2, 1, N'リーダー')
INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (3, 1, N'サブリーダー')
INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (4, 1, N'SE')
INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (5, 1, N'PG')
INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (6, 1, N'アシスタント')
INSERT INTO m_assign (assign_id, active, assign_desc) VALUES (7, 1, N'他')

INSERT INTO m_dbms (dbms_id, active, dbms_desc) VALUES (1, 1, N'Oracle')
INSERT INTO m_dbms (dbms_id, active, dbms_desc) VALUES (2, 1, N'MongoDB')
INSERT INTO m_dbms (dbms_id, active, dbms_desc) VALUES (3, 1, N'MySQL')
INSERT INTO m_dbms (dbms_id, active, dbms_desc) VALUES (4, 1, N'SQL Server')
INSERT INTO m_dbms (dbms_id, active, dbms_desc) VALUES (5, 1, N'PostgreSQL')
INSERT INTO m_dbms (dbms_id, active, dbms_desc) VALUES (6, 1, N'その他')

INSERT INTO m_kuruma (id, active, car_desc) VALUES (1, 1, N'ー')
INSERT INTO m_kuruma (id, active, car_desc) VALUES (2, 1, N'軽自動車')
INSERT INTO m_kuruma (id, active, car_desc) VALUES (3, 1, N'普通自動車')

INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (1, 1, N'Java')
INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (2, 1, N'JavaScript')
INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (3, 1, N'PHP')
INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (4, 1, N'Node')
INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (5, 1, N'HTML/CSS')
INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (6, 1, N'C++')
INSERT INTO m_lang (lang_id, active, lang_desc) VALUES (7, 1, N'C#')

INSERT INTO m_maker (maker_id, active, maker_desc) VALUES (1, 1, N'MAC')
INSERT INTO m_maker (maker_id, active, maker_desc) VALUES (2, 1, N'ANDROID')
INSERT INTO m_maker (maker_id, active, maker_desc) VALUES (3, 1, N'DOS/V')

INSERT INTO m_os (os_id, active, os_desc) VALUES (1, 1, N'Windows 10')
INSERT INTO m_os (os_id, active, os_desc) VALUES (2, 1, N'Linux')
INSERT INTO m_os (os_id, active, os_desc) VALUES (3, 1, N'Windows 7')
INSERT INTO m_os (os_id, active, os_desc) VALUES (4, 0, N'Ubuntu')
INSERT INTO m_os (os_id, active, os_desc) VALUES (5, 1, N'Windows Server')
INSERT INTO m_os (os_id, active, os_desc) VALUES (6, 1, N'Windows XP')
INSERT INTO m_os (os_id, active, os_desc) VALUES (7, 1, N'ホスト系')
INSERT INTO m_os (os_id, active, os_desc) VALUES (8, 1, N'UNIX')

INSERT INTO m_tools (tools_id, active, tools_desc) VALUES (1, 1, N'Chrome')
INSERT INTO m_tools (tools_id, active, tools_desc) VALUES (2, 1, N'Excel')
INSERT INTO m_tools (tools_id, active, tools_desc) VALUES (3, 1, N'IE')
INSERT INTO m_tools (tools_id, active, tools_desc) VALUES (4, 1, N'Safari')
INSERT INTO m_tools (tools_id, active, tools_desc) VALUES (5, 1, N'Opera')
INSERT INTO m_tools (tools_id, active, tools_desc) VALUES (6, 1, N'Access')

INSERT INTO m_warea (warea_id, active, warea_desc) VALUES (1, 1, N'山口県')
INSERT INTO m_warea (warea_id, active, warea_desc) VALUES (2, 1, N'福岡県')
INSERT INTO m_warea (warea_id, active, warea_desc) VALUES (3, 1, N'島根県')

INSERT INTO m_yaku (position_id, active, position_desc) VALUES (1, 1, N'代表取締役')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (2, 1, N'取締役')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (3, 1, N'事業部長')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (4, 1, N'部長')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (5, 1, N'部長代理')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (6, 1, N'課長')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (7, 1, N'課長代理')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (8, 1, N'マネージャ')
INSERT INTO m_yaku (position_id, active, position_desc) VALUES (9, 1, N'ー')

INSERT INTO m_shozoku (affiliation_id, active, affiliation_desc) VALUES (1, 1, N'開発センター')
INSERT INTO m_shozoku (affiliation_id, active, affiliation_desc) VALUES (2, 1, N'経営部門')
INSERT INTO m_shozoku (affiliation_id, active, affiliation_desc) VALUES (3, 1, N'総務ソリューショングループ')
INSERT INTO m_shozoku (affiliation_id, active, affiliation_desc) VALUES (4, 1, N'ビジネスソリューショングループ')
INSERT INTO m_shozoku (affiliation_id, active, affiliation_desc) VALUES (5, 1, N'産業ソリューショングループ')
INSERT INTO m_shozoku (affiliation_id, active, affiliation_desc) VALUES (6, 1, N'ＧＩＳソリューショングループ')

INSERT INTO m_response (response_id, active, response_desc) VALUES (1, 1, N'コンサルタント')
INSERT INTO m_response (response_id, active, response_desc) VALUES (2, 1, N'メンテナンス作業')
INSERT INTO m_response (response_id, active, response_desc) VALUES (3, 1, N'講師')
INSERT INTO m_response (response_id, active, response_desc) VALUES (4, 1, N'インストラクター')
INSERT INTO m_response (response_id, active, response_desc) VALUES (5, 1, N'ＳＥサポート')
INSERT INTO m_response (response_id, active, response_desc) VALUES (6, 1, N'システム運用管理')
INSERT INTO m_response (response_id, active, response_desc) VALUES (7, 1, N'オペレーション')
INSERT INTO m_response (response_id, active, response_desc) VALUES (8, 1, N'マニュアル作成')
INSERT INTO m_response (response_id, active, response_desc) VALUES (9, 1, N'他')
INSERT INTO m_response (response_id, active, response_desc) VALUES (10, 1, N'システム監査')
INSERT INTO m_response (response_id, active, response_desc) VALUES (11, 1, N'調査分析')
INSERT INTO m_response (response_id, active, response_desc) VALUES (12, 1, N'基本設計')
INSERT INTO m_response (response_id, active, response_desc) VALUES (13, 1, N'詳細設計')
INSERT INTO m_response (response_id, active, response_desc) VALUES (14, 1, N'プログラム設計')
INSERT INTO m_response (response_id, active, response_desc) VALUES (15, 1, N'プログラミング')
INSERT INTO m_response (response_id, active, response_desc) VALUES (16, 1, N'単体テスト')
INSERT INTO m_response (response_id, active, response_desc) VALUES (17, 1, N'結合テスト')

INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (1, 1, N'自治体')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (2, 1, N'官庁')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (3, 1, N'文教')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (4, 1, N'公共')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (5, 1, N'金融保険')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (6, 1, N'医療')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (7, 1, N'運輸')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (8, 1, N'流通')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (9, 1, N'製造')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (10, 1, N'サービス')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (11, 1, N'ＧＩＳ')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (12, 1, N'その他')
INSERT INTO m_gyoumu (industry_type_id, active, industry_type_desc) VALUES (13, 1, N'社内')

INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 1, 1, N'収納')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 2, 1, N'国民年金')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 3, 1, N'e?Learning')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 4, 1, N'上水道')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 5, 1, N'損害保険')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 6, 1, N'医療統計')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 7, 1, N'物流管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 8, 1, N'経理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 9, 1, N'部品管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 10, 1, N'福祉施設')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 11, 1, N'農地情報管理システム')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 12, 1, N'汎用データ管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (1, 13, 1, N'グループウェア')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 1, 1, N'滞納')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 2, 1, N'厚生年金')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 3, 1, N'名簿管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 4, 1, N'下水道')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 5, 1, N'保険管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 6, 1, N'入院予約')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 7, 1, N'タクシー業務管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 8, 1, N'販売管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 9, 1, N'購買管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 10, 1, N'ＰＯＳシステム')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 11, 1, N'森林資源管理システム')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 12, 1, N'就職支援')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (2, 13, 1, N'顧客管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 1, 1, N'住民記録')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 2, 1, N'特許出願')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 3, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 4, 1, N'河川管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 5, 1, N'融資支援')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 6, 1, N'退院サマリ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 7, 1, N'給与管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 8, 1, N'給与管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 9, 1, N'販売管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 10, 1, N'受発注管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 11, 1, N'エリアマーケティング')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 12, 1, N'ＣＲＭ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (3, 13, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 1, 1, N'保育料')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 2, 1, N'ＭＡＰ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 4, 1, N'浄水場管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 5, 1, N'開発管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 6, 1, N'輸血同意管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 7, 1, N'予約管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 8, 1, N'顧客与信管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 9, 1, N'営放')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 10, 1, N'物流支援')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 11, 1, N'画像解析')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (4, 12, 1, N'測量設計支援')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 1, 1, N'国民年金')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 2, 1, N'維持販売')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 4, 1, N'計量搬入')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 5, 1, N'口座振替ＦＤ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 6, 1, N'ドック予約')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 7, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 8, 1, N'会員管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 9, 1, N'電力監視')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 10, 1, N'ＣＩＳ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (5, 12, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 1, 1, N'国民健康保険')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 2, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 4, 1, N'揚運炭管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 5, 1, N'オンライン試算照会')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 6, 1, N'健康管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 8, 1, N'在庫管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 9, 1, N'連続炉ＦＡ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (6, 10, 1, N'申請書類ｼｽﾃﾑ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 1, 1, N'固定資産税')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 4, 1, N'文書管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 5, 1, N'既契約管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 6, 1, N'手術室')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 8, 1, N'青果卸')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 9, 1, N'ＰＡ制御')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (7, 10, 1, N'見積支援ｼｽﾃﾑ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 1, 1, N'法人税')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 4, 1, N'浄化槽')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 5, 1, N'協同組合商品')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 6, 1, N'収納管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 8, 1, N'売上仕入管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 9, 1, N'箔プロコン')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (8, 10, 1, N'ｺｰﾙｾﾝﾀｰｼｽﾃﾑ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 1, 1, N'住民税')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 4, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 5, 1, N'定期保険')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 6, 1, N'パネル管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 8, 1, N'在庫受払管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 9, 1, N'２Ｚプロコン')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (9, 10, 1, N'船積書類作成ｼｽﾃﾑ')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (10, 1, 1, N'軽自動車税')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (10, 5, 1, N'逓増定期保険')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (10, 6, 1, N'医療事務')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (10, 8, 1, N'売掛買掛管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (10, 9, 1, N'整備ラインプロコン')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (10, 10, 1, N'設備管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (11, 1, 1, N'起債管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (11, 5, 1, N'ガン保険')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (11, 6, 1, N'固定資産')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (11, 8, 1, N'売上総利益')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (11, 9, 1, N'高炉プロコン')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (11, 10, 1, N'技術用教育')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (12, 1, 1, N'人事給与')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (12, 5, 1, N'リスクアセット算出')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (12, 6, 1, N'名簿管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (12, 8, 1, N'一般会計管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (12, 9, 1, N'厚板剪断プロコン')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (12, 10, 1, N'報告書作成承認支援')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (13, 1, 1, N'財務会計')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (13, 5, 1, N'対外接続')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (13, 6, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (13, 8, 1, N'情報検索')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (13, 9, 1, N'ブロードキャスト伝送')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (13, 10, 1, N'顧客管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (14, 1, 1, N'老人保健')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (14, 5, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (14, 8, 1, N'商品情報管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (14, 9, 1, N'ﾏｲｺﾝﾁｯﾌﾟﾊﾟﾗﾒｰﾀ測定')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (14, 10, 1, N'物件管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (15, 1, 1, N'介護保険')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (15, 8, 1, N'物流EDI')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (15, 9, 1, N'統合仕様')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (15, 10, 1, N'グロス管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (16, 1, 1, N'老人医療')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (16, 8, 1, N'受発注管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (16, 9, 1, N'運用監視')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (16, 10, 1, N'会計管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (17, 1, 1, N'高齢者医療')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (17, 8, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (17, 9, 1, N'工程管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (17, 10, 1, N'車両管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (18, 1, 1, N'福祉医療')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (18, 9, 1, N'物流統合')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (18, 10, 1, N'給与管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (19, 1, 1, N'障害者医療')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (19, 9, 1, N'計画値')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (19, 10, 1, N'店舗管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (20, 1, 1, N'市民検診')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (20, 9, 1, N'基準値')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (20, 10, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (21, 1, 1, N'生活保護')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (21, 9, 1, N'設備保全')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (22, 1, 1, N'戸籍')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (22, 9, 1, N'トラックスーケル')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (23, 1, 1, N'共済会')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (23, 9, 1, N'生産管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (24, 1, 1, N'健康管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (24, 9, 1, N'その他')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (25, 1, 1, N'市立大学')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (26, 1, 1, N'畜犬管理')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (27, 1, 1, N'在宅高齢者')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (28, 1, 1, N'児童手当')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (29, 1, 1, N'児童扶養手当')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (30, 1, 1, N'交通共済')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (31, 1, 1, N'心身障害者')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (32, 1, 1, N'支援費')
INSERT INTO m_gykubun (industry_class_id, industry_type_id, active, industry_class_desc) VALUES (33, 1, 1, N'その他')

insert into m_shain (sha_no, sha_address, sha_birthday, sha_blood, sha_deleteflg, sha_homephonenumber, sha_entryday, sha_kana, sha_mail, sha_married, sha_mobilemail, sha_mobilephonenumber, sha_name, sha_notes, sha_password, sha_postalcode, sha_recruit, sha_resist, sha_resister, sha_retireflg, sha_retireday, sha_sex, sha_support, sha_position, sha_resume, sha_authflag, sha_avaliablearea, sha_carmodel) VALUES (N'105325', N'administrator', CAST(N'2018-03-11T00:00:00.0000000' AS DateTime2), NULL, 0, N'administrator', CAST(N'2018-12-28T00:00:00.0000000' AS DateTime2), N'administrator', N'administrator', 0, N'administrator', N'administrator', N'administrator', NULL, N'$2a$10$zFleGzL2B.zFl3Oal.u3Z.uWbeAlVKhCfDro6Bn60cYWW9vOwQzZi', N'755-0151', N'1', NULL, NULL, 0, NULL, N'男', 0, 1, NULL, 1, 1, 1)