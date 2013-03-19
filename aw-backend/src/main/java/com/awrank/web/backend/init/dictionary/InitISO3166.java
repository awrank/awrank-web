package com.awrank.web.backend.init.dictionary;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Dictionary initialization
 *
 * @author Alex Polyakov
 * @author Andrew Stoyaltsev
 */
@Service
public class InitISO3166 {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void init() {
		final List<Dictionary> list = new ArrayList<Dictionary>();
		list.add(new Dictionary(null, Language.RU, "COUNTRY_XX", "Не определено"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_XX", "Undefined"));
//      COUNTRY
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AD", "Андорра"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AD", "Andorra"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AE", "ОАЭ"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AE", "United Arab Emirates"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AF", "Афганистан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AF", "Afghanistan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AG", "Антигуа и Барбуда"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AG", "Antigua and Barbuda"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AI", "Ангилья"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AI", "Anguilla"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AL", "Албания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AL", "Albania"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AM", "Армения"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AM", "Armenia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AO", "Ангола"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AO", "Angola"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AQ", "Антарктида"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AQ", "Antarctica"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AR", "Аргентина"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AR", "Argentina"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AS", "Американское Самоа"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AS", "American Samoa"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AT", "Австрия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AT", "Austria"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AU", "Австралия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AU", "Australia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AW", "Аруба"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AW", "Aruba"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AX", "Аландские острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AX", "Åland Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_AZ", "Азербайджан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_AZ", "Azerbaijan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BA", "Босния и Герцеговина"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BA", "Bosnia and Herzegovina"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BB", "Барбадос"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BB", "Barbados"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BD", "Бангладеш"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BD", "Bangladesh"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BE", "Бельгия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BE", "Belgium"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BF", "Буркина-Фасо"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BF", "Burkina Faso"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BG", "Болгария"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BG", "Bulgaria"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BH", "Бахрейн"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BH", "Bahrain"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BI", "Бурунди"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BI", "Burundi"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BJ", "Бенин"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BJ", "Benin"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BL", "Сен-Бартелеми"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BL", "Saint Barthélemy"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BM", "Бермуды"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BM", "Bermuda"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BN", "Бруней"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BN", "Brunei Darussalam"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BO", "Боливия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BO", "Bolivia, Plurinational State of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BQ", "Бонэйр, Синт-Эстатиус и Саба"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BQ", "Bonaire, Sint Eustatius and Saba"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BR", "Бразилия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BR", "Brazil"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BS", "Багамы"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BS", "Bahamas"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BT", "Бутан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BT", "Bhutan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BV", "Остров Буве"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BV", "Bouvet Island"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BW", "Ботсвана"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BW", "Botswana"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BY", "Белоруссия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BY", "Belarus"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_BZ", "Белиз"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_BZ", "Belize"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CA", "Канада"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CA", "Canada"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CC", "Кокосовые острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CC", "Cocos (Keeling) Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CD", "ДР Конго"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CD", "Congo, the Democratic Republic of the"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CF", "ЦАР"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CF", "Central African Republic"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CG", "Республика Конго"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CG", "Congo"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CH", "Швейцария"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CH", "Switzerland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CI", "Кот-д’Ивуар"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CI", "Côte d'Ivoire"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CK", "Острова Кука"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CK", "Cook Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CL", "Чили"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CL", "Chile"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CM", "Камерун"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CM", "Cameroon"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CN", "КНР"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CN", "China"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CO", "Колумбия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CO", "Colombia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CR", "Коста-Рика"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CR", "Costa Rica"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CU", "Куба"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CU", "Cuba"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CV", "Кабо-Верде"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CV", "Cape Verde"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CW", "Кюрасао"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CW", "Curaçao"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CX", "Остров Рождества"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CX", "Christmas Island"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CY", "Кипр"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CY", "Cyprus"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_CZ", "Чехия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_CZ", "Czech Republic"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_DE", "Германия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_DE", "Germany"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_DJ", "Джибути"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_DJ", "Djibouti"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_DK", "Дания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_DK", "Denmark"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_DM", "Доминика"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_DM", "Dominica"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_DO", "Доминиканская Республика"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_DO", "Dominican Republic"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_DZ", "Алжир"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_DZ", "Algeria"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_EC", "Эквадор"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_EC", "Ecuador"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_EE", "Эстония"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_EE", "Estonia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_EG", "Египет"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_EG", "Egypt"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_EH", "Западная Сахара"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_EH", "Western Sahara"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ER", "Эритрея"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ER", "Eritrea"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ES", "Испания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ES", "Spain"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ET", "Эфиопия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ET", "Ethiopia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_EU", "Европейский союз"));

		list.add(new Dictionary(null, Language.RU, "COUNTRY_FI", "Финляндия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_FI", "Finland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_FJ", "Фиджи"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_FJ", "Fiji"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_FK", "Фолклендские острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_FK", "Falkland Islands (Malvinas)"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_FM", "Микронезия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_FM", "Micronesia, Federated States of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_FO", "Фарерские острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_FO", "Faroe Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_FR", "Франция"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_FR", "France"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GA", "Габон"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GA", "Gabon"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GB", "Великобритания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GB", "United Kingdom"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GD", "Гренада"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GD", "Grenada"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GE", "Грузия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GE", "Georgia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GF", "Гвиана"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GF", "French Guiana"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GG", "Гернси"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GG", "Guernsey"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GH", "Гана"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GH", "Ghana"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GI", "Гибралтар"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GI", "Gibraltar"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GL", "Гренландия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GL", "Greenland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GM", "Гамбия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GM", "Gambia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GN", "Гвинея"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GN", "Guinea"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GP", "Гваделупа"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GP", "Guadeloupe"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GQ", "Экваториальная Гвинея"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GQ", "Equatorial Guinea"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GR", "Греция"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GR", "Greece"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GS", "Южная Георгия и Южные Сандвичевы острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GS", "South Georgia and the South Sandwich Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GT", "Гватемала"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GT", "Guatemala"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GU", "Гуам"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GU", "Guam"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GW", "Гвинея-Бисау"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GW", "Guinea-Bissau"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_GY", "Гайана"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_GY", "Guyana"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_HK", "Гонконг"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_HK", "Hong Kong"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_HM", "Херд и Макдональд"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_HM", "Heard Island and McDonald Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_HN", "Гондурас"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_HN", "Honduras"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_HR", "Хорватия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_HR", "Croatia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_HT", "Гаити"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_HT", "Haiti"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_HU", "Венгрия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_HU", "Hungary"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ID", "Индонезия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ID", "Indonesia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IE", "Ирландия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IE", "Ireland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IL", "Израиль"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IL", "Israel"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IM", "Остров Мэн"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IM", "Isle of Man"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IN", "Индия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IN", "India"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IO", "Британская территория в Индийском океане"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IO", "British Indian Ocean Territory"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IQ", "Ирак"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IQ", "Iraq"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IR", "Иран"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IR", "Iran, Islamic Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IS", "Исландия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IS", "Iceland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_IT", "Италия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_IT", "Italy"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_JE", "Джерси"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_JE", "Jersey"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_JM", "Ямайка"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_JM", "Jamaica"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_JO", "Иордания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_JO", "Jordan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_JP", "Япония"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_JP", "Japan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KE", "Кения"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KE", "Kenya"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KG", "Киргизия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KG", "Kyrgyzstan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KH", "Камбоджа"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KH", "Cambodia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KI", "Кирибати"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KI", "Kiribati"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KM", "Коморы"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KM", "Comoros"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KN", "Сент-Китс и Невис"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KN", "Saint Kitts and Nevis"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KP", "КНДР"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KP", "Korea, Democratic People's Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KR", "Республика Корея"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KR", "Korea, Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KW", "Кувейт"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KW", "Kuwait"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KY", "Каймановы острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KY", "Cayman Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_KZ", "Казахстан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_KZ", "Kazakhstan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LA", "Лаос"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LA", "Lao People's Democratic Republic"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LB", "Ливан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LB", "Lebanon"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LC", "Сент-Люсия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LC", "Saint Lucia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LI", "Лихтенштейн"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LI", "Liechtenstein"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LK", "Шри-Ланка"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LK", "Sri Lanka"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LR", "Либерия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LR", "Liberia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LS", "Лесото"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LS", "Lesotho"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LT", "Литва"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LT", "Lithuania"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LU", "Люксембург"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LU", "Luxembourg"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LV", "Латвия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LV", "Latvia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_LY", "Ливия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_LY", "Libya"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MA", "Марокко"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MA", "Morocco"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MC", "Монако"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MC", "Monaco"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MD", "Молдавия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MD", "Moldova, Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ME", "Черногория"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ME", "Montenegro"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MF", "Сен-Мартен"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MF", "Saint Martin (French part)"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MG", "Мадагаскар"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MG", "Madagascar"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MH", "Маршалловы Острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MH", "Marshall Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MK", "Македония"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MK", "Macedonia, The Former Yugoslav Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ML", "Мали"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ML", "Mali"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MM", "Мьянма"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MM", "Myanmar"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MN", "Монголия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MN", "Mongolia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MO", "Макао"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MO", "Macao"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MP", "Северные Марианские острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MP", "Northern Mariana Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MQ", "Мартиника"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MQ", "Martinique"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MR", "Мавритания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MR", "Mauritania"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MS", "Монтсеррат"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MS", "Montserrat"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MT", "Мальта"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MT", "Malta"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MU", "Маврикий"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MU", "Mauritius"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MV", "Мальдивы"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MV", "Maldives"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MW", "Малави"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MW", "Malawi"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MX", "Мексика"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MX", "Mexico"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MY", "Малайзия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MY", "Malaysia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_MZ", "Мозамбик"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_MZ", "Mozambique"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NA", "Намибия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NA", "Namibia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NC", "Новая Каледония"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NC", "New Caledonia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NE", "Нигер"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NE", "Niger"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NF", "Остров Норфолк"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NF", "Norfolk Island"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NG", "Нигерия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NG", "Nigeria"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NI", "Никарагуа"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NI", "Nicaragua"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NL", "Нидерланды"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NL", "Netherlands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NO", "Норвегия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NO", "Norway"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NP", "Непал"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NP", "Nepal"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NR", "Науру"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NR", "Nauru"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NU", "Ниуэ"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NU", "Niue"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_NZ", "Новая Зеландия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_NZ", "New Zealand"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_OM", "Оман"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_OM", "Oman"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PA", "Панама"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PA", "Panama"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PE", "Перу"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PE", "Peru"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PF", "Французская Полинезия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PF", "French Polynesia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PG", "Папуа — Новая Гвинея"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PG", "Papua New Guinea"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PH", "Филиппины"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PH", "Philippines"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PK", "Пакистан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PK", "Pakistan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PL", "Польша"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PL", "Poland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PM", "Сен-Пьер и Микелон"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PM", "Saint Pierre and Miquelon"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PN", "Острова Питкэрн"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PN", "Pitcairn"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PR", "Пуэрто-Рико"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PR", "Puerto Rico"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PS", "Государство Палестина"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PS", "Palestine, State of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PT", "Португалия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PT", "Portugal"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PW", "Палау"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PW", "Palau"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_PY", "Парагвай"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_PY", "Paraguay"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_QA", "Катар"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_QA", "Qatar"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_RE", "Реюньон"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_RE", "Réunion"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_RO", "Румыния"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_RO", "Romania"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_RS", "Сербия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_RS", "Serbia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_RU", "Россия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_RU", "Russian Federation"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_RW", "Руанда"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_RW", "Rwanda"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SA", "Саудовская Аравия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SA", "Saudi Arabia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SB", "Соломоновы Острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SB", "Solomon Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SC", "Сейшельские Острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SC", "Seychelles"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SD", "Судан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SD", "Sudan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SE", "Швеция"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SE", "Sweden"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SG", "Сингапур"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SG", "Singapore"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SH", "Острова Святой Елены, Вознесения и Тристан-да-Кунья"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SH", "Saint Helena, Ascension and Tristan da Cunha"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SI", "Словения"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SI", "Slovenia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SJ", "Шпицберген и Ян-Майен"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SJ", "Svalbard and Jan Mayen"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SK", "Словакия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SK", "Slovakia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SL", "Сьерра-Леоне"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SL", "Sierra Leone"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SM", "Сан-Марино"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SM", "San Marino"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SN", "Сенегал"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SN", "Senegal"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SO", "Сомали"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SO", "Somalia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SR", "Суринам"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SR", "Suriname"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SS", "Южный Судан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SS", "South Sudan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ST", "Сан-Томе и Принсипи"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ST", "Sao Tome and Principe"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SU", "СССР (до сентября 1992 года)"));

		list.add(new Dictionary(null, Language.RU, "COUNTRY_SV", "Сальвадор"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SV", "El Salvador"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SX", "Синт-Мартен"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SX", "Sint Maarten (Dutch part)"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SY", "Сирия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SY", "Syrian Arab Republic"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_SZ", "Свазиленд"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_SZ", "Swaziland"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TC", "Тёркс и Кайкос"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TC", "Turks and Caicos Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TD", "Чад"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TD", "Chad"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TF", "Французские Южные и Антарктические Территории"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TF", "French Southern Territories"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TG", "Того"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TG", "Togo"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TH", "Таиланд"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TH", "Thailand"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TJ", "Таджикистан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TJ", "Tajikistan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TK", "Токелау"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TK", "Tokelau"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TL", "Восточный Тимор"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TL", "Timor-Leste"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TM", "Туркмения"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TM", "Turkmenistan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TN", "Тунис"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TN", "Tunisia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TO", "Тонга"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TO", "Tonga"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TR", "Турция"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TR", "Turkey"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TT", "Тринидад и Тобаго"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TT", "Trinidad and Tobago"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TV", "Тувалу"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TV", "Tuvalu"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TW", "Китайская Республика"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TW", "Taiwan, Province of China"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_TZ", "Танзания"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_TZ", "Tanzania, United Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_UA", "Украина"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_UA", "Ukraine"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_UG", "Уганда"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_UG", "Uganda"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_UM", "Внешние малые острова (США)"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_UM", "United States Minor Outlying Islands"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_US", "США"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_US", "United States"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_UY", "Уругвай"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_UY", "Uruguay"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_UZ", "Узбекистан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_UZ", "Uzbekistan"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VA", "Ватикан"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VA", "Holy See (Vatican City State)"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VC", "Сент-Винсент и Гренадины"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VC", "Saint Vincent and the Grenadines"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VE", "Венесуэла"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VE", "Venezuela, Bolivarian Republic of"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VG", "Британские Виргинские острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VG", "Virgin Islands, British"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VI", "Американские Виргинские острова"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VI", "Virgin Islands, U.S."));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VN", "Вьетнам"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VN", "Viet Nam"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_VU", "Вануату"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_VU", "Vanuatu"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_WF", "Уоллис и Футуна"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_WF", "Wallis and Futuna"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_WS", "Самоа"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_WS", "Samoa"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_YE", "Йемен"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_YE", "Yemen"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_YT", "Майотта"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_YT", "Mayotte"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ZA", "ЮАР"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ZA", "South Africa"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ZM", "Замбия"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ZM", "Zambia"));
		list.add(new Dictionary(null, Language.RU, "COUNTRY_ZW", "Зимбабве"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY_ZW", "Zimbabwe"));

		for (final Dictionary item : list) {
			Dictionary oldItem = dictionaryDao.findOneByLanguageAndCode(item.getLanguage(), item.getCode());
			if (oldItem != null) {
				if (!oldItem.getText().equals(item.getText())) {
					oldItem.setText(item.getText());
					dictionaryDao.save(oldItem);
				}
			} else {
				dictionaryDao.save(item);
			}
		}
	}
}