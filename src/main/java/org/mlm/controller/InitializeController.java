package org.mlm.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mlm.dynamicjob.EventTask;
import org.mlm.dynamicjob.MyTask;
import org.mlm.model.dto.EventForm;
import org.mlm.model.entity.Category;
import org.mlm.model.entity.MasterCategory;
import org.mlm.model.entity.Role;
import org.mlm.model.entity.User;
import org.mlm.model.json.UserJTable;
import org.mlm.service.CategoriesService;
import org.mlm.service.EventsService;
import org.mlm.service.UserService;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * Dodanie uzytkowanika z prawami admina.
 * Nie³adne to jest ale narazie wystarczy.
 * 
 * @author matrom
 *
 */
@Controller
public class InitializeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventsService eventService;
	
	@Autowired
	private ConversionService conversionService;
	
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	
	@RequestMapping(value="/initialize")
	public ModelAndView initialize(){
		
		//drop database mlm_db;
		//create database mlm_db;
		
		//Shift+ prawy -> otworz okno polecenia tutaj
		//D:\nasza_strona\mysql-5.6.17-win32\data
		//mysqld
		//-> zamknij okienko
		//nowe okienko
		//mysql -h localhost -u bodziek -p mlm_db;
		
		/*inicjalizacja smiga- najpierw drop database, create database, nastepnie restart serwera, nastepnie
		 * MLMTestProject2/initialize */
		
		/*************************users*********************/
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		
		User matrom = new User();
		matrom.setEmail("admin@admin");
		matrom.setEnabled(true);
		matrom.setPassword("password");
		matrom.setRoles(roles);
		matrom.setUserName("matrom");
		userService.createUser(matrom);
		
		
		User bodziek = new User();
		bodziek.setEmail("bodziek@admin");
		bodziek.setEnabled(true);
		bodziek.setPassword("bogusz6");
		bodziek.setRoles(roles);
		bodziek.setUserName("bodziek");
		//nie denerwuj sie matrom
		UserJTable userJson= new UserJTable(bodziek);
		User userModel = conversionService.convert(userJson, User.class);
		userService.createUser(userModel);
		
		User leszek = new User();
		leszek.setEmail("calormen@admin");
		leszek.setEnabled(true);
		leszek.setPassword("qwerty");
		leszek.setRoles(roles);
		leszek.setUserName("calormen");
		userJson= new UserJTable(leszek);
		userModel = conversionService.convert(userJson, User.class);
		userService.createUser(userModel);
		/****************************************************/
		
		/**********************categories********************/
		/*CategoryModel pilka = new CategoryModel();
		pilka.setCategoryIconPath("/resources/categories/images/pilka.png");
		pilka.setCategoryId(0);
		pilka.setCategoryName("pi³ka");
		categoriesService.createCategory(pilka);
		
		CategoryModel tenis = new CategoryModel();
		tenis.setCategoryIconPath("/resources/categories/images/pilka_tenisowa.png");
		tenis.setCategoryId(0);
		tenis.setCategoryName("tenis");
		categoriesService.createCategory(tenis);
		
		CategoryModel kino = new CategoryModel();
		kino.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		kino.setCategoryId(0);
		kino.setCategoryName("kino");
		categoriesService.createCategory(kino);
		
		CategoryModel podroz = new CategoryModel();
		podroz.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		podroz.setCategoryId(0);
		podroz.setCategoryName("podroz");
		categoriesService.createCategory(podroz);
		
		CategoryModel wycieczka = new CategoryModel();
		wycieczka.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		wycieczka.setCategoryId(0);
		wycieczka.setCategoryName("wycieczka");
		categoriesService.createCategory(wycieczka);
		
		CategoryModel spacer = new CategoryModel();
		spacer.setCategoryIconPath("/resources/categories/images/pilka_tenisowa.png");
		spacer.setCategoryId(0);
		spacer.setCategoryName("spacer");
		categoriesService.createCategory(spacer);
		
		CategoryModel karty = new CategoryModel();
		karty.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		karty.setCategoryId(0);
		karty.setCategoryName("karty");
		categoriesService.createCategory(karty);*/
		
		MasterCategory gryTowarzyskie = new MasterCategory();
		gryTowarzyskie.setId(0);
		gryTowarzyskie.setName("Gry towarzyskie");
		gryTowarzyskie.setIconPath("/resources/categories/images/kwiatek.png");
		categoriesService.createMasterCategory(gryTowarzyskie);
		
		MasterCategory przejazdy = new MasterCategory();
		przejazdy.setId(0);
		przejazdy.setName("Przejazdy");
		przejazdy.setIconPath("/resources/categories/images/kwiatek.png");
		categoriesService.createMasterCategory(przejazdy);
		
		MasterCategory rozrywka = new MasterCategory();
		rozrywka.setId(0);
		rozrywka.setName("Rozrywka");
		rozrywka.setIconPath("/resources/categories/images/kwiatek.png");
		categoriesService.createMasterCategory(rozrywka);
		
		MasterCategory gastronomia = new MasterCategory();
		gastronomia.setId(0);
		gastronomia.setName("Gastronomia");
		gastronomia.setIconPath("/resources/categories/images/kwiatek.png");
		categoriesService.createMasterCategory(gastronomia);
		
		MasterCategory sport = new MasterCategory();
		sport.setId(0);
		sport.setName("Sport");
		sport.setIconPath("/resources/categories/images/kwiatek.png");
		categoriesService.createMasterCategory(sport);
		
		MasterCategory podroze_i_turystyka = new MasterCategory();
		podroze_i_turystyka.setId(0);
		podroze_i_turystyka.setName("Podró¿e i turystyka");
		podroze_i_turystyka.setIconPath("/resources/categories/images/kwiatek.png");
		categoriesService.createMasterCategory(podroze_i_turystyka);
		
		
		
		/*Gry towarzyskie: gry karciane, gry planszowe, gry komputerowe, bilard, krêgle, inne*/	
		MasterCategory masterCategory = categoriesService.getMastercategoryByName(gryTowarzyskie.getName());
		
		Category gry_karciane = new Category();
		gry_karciane.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		gry_karciane.setCategoryId(0);
		gry_karciane.setCategoryName("Gry karciane");
		gry_karciane.setMasterCategory(masterCategory);
		categoriesService.createCategory(gry_karciane);
		
		Category gry_planszowe = new Category();
		gry_planszowe.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		gry_planszowe.setCategoryId(0);
		gry_planszowe.setCategoryName("Gry planszowe");
		gry_planszowe.setMasterCategory(masterCategory);
		categoriesService.createCategory(gry_planszowe);
		
		Category gry_komputerowe = new Category();
		gry_komputerowe.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		gry_komputerowe.setCategoryId(0);
		gry_komputerowe.setCategoryName("Gry komputerowe");
		gry_komputerowe.setMasterCategory(masterCategory);
		categoriesService.createCategory(gry_komputerowe);
		
		Category bilard = new Category();
		bilard.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		bilard.setCategoryId(0);
		bilard.setCategoryName("Bilard");
		bilard.setMasterCategory(masterCategory);
		categoriesService.createCategory(bilard);
		
		Category kregle = new Category();
		kregle.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		kregle.setCategoryId(0);
		kregle.setCategoryName("Krêgle");
		kregle.setMasterCategory(masterCategory);
		categoriesService.createCategory(kregle);
		
		Category gry_inne = new Category();
		gry_inne.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		gry_inne.setCategoryId(0);
		gry_inne.setCategoryName("Gry - inne");
		gry_inne.setMasterCategory(masterCategory);
		categoriesService.createCategory(gry_inne);
		
		/*Przejazdy: samochodem, kolej¹, autobusem, samolotem, morskie*/
		masterCategory = categoriesService.getMastercategoryByName(przejazdy.getName());
		
		Category przejazdy_samochodem = new Category();
		przejazdy_samochodem.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		przejazdy_samochodem.setCategoryId(0);
		przejazdy_samochodem.setCategoryName("Samochodem");
		przejazdy_samochodem.setMasterCategory(masterCategory);
		categoriesService.createCategory(przejazdy_samochodem);
		
		Category przejazdy_koleja = new Category();
		przejazdy_koleja.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		przejazdy_koleja.setCategoryId(0);
		przejazdy_koleja.setCategoryName("Kolej¹");
		przejazdy_koleja.setMasterCategory(masterCategory);
		categoriesService.createCategory(przejazdy_koleja);
		
		Category przejazdy_autobusem = new Category();
		przejazdy_autobusem.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		przejazdy_autobusem.setCategoryId(0);
		przejazdy_autobusem.setCategoryName("Autobusem");
		przejazdy_autobusem.setMasterCategory(masterCategory);
		categoriesService.createCategory(przejazdy_autobusem);
		
		Category podroz_samolotem = new Category();
		podroz_samolotem.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		podroz_samolotem.setCategoryId(0);
		podroz_samolotem.setCategoryName("Samolotem");
		podroz_samolotem.setMasterCategory(masterCategory);
		categoriesService.createCategory(podroz_samolotem);
		
		Category podroz_morska = new Category();
		podroz_morska.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		podroz_morska.setCategoryId(0);
		podroz_morska.setCategoryName("Morskie");
		podroz_morska.setMasterCategory(masterCategory);
		categoriesService.createCategory(podroz_morska);
		
		/*Rozrywka: filmy, przedstawienia, muzyka, tañce, clubbing*/
		masterCategory = categoriesService.getMastercategoryByName(rozrywka.getName());
		
		Category filmy = new Category();
		filmy.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		filmy.setCategoryId(0);
		filmy.setCategoryName("Filmy");
		filmy.setMasterCategory(masterCategory);
		categoriesService.createCategory(filmy);
		
		Category przedstawienia = new Category();
		przedstawienia.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		przedstawienia.setCategoryId(0);
		przedstawienia.setCategoryName("Przedstawienia");
		przedstawienia.setMasterCategory(masterCategory);
		categoriesService.createCategory(przedstawienia);
		
		Category muzyka = new Category();
		muzyka.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		muzyka.setCategoryId(0);
		muzyka.setCategoryName("Muzyka");
		muzyka.setMasterCategory(masterCategory);
		categoriesService.createCategory(muzyka);
		
		Category tance = new Category();
		tance.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		tance.setCategoryId(0);
		tance.setCategoryName("Tañce");
		tance.setMasterCategory(masterCategory);
		categoriesService.createCategory(tance);
		
		Category clubbing = new Category();
		clubbing.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		clubbing.setCategoryId(0);
		clubbing.setCategoryName("Clubbing");
		clubbing.setMasterCategory(masterCategory);
		categoriesService.createCategory(clubbing);
		
		/*Gastronomia: restauracje, fast foody, puby i bary, kawiarnie, cukiernie, lodziarnie, plenerowe*/
		masterCategory = categoriesService.getMastercategoryByName(gastronomia.getName());
		
		Category restauracje = new Category();
		restauracje.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		restauracje.setCategoryId(0);
		restauracje.setCategoryName("Restauracje");
		restauracje.setMasterCategory(masterCategory);
		categoriesService.createCategory(restauracje);
		
		Category fast_foody = new Category();
		fast_foody.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		fast_foody.setCategoryId(0);
		fast_foody.setCategoryName("Fast foody");
		fast_foody.setMasterCategory(masterCategory);
		categoriesService.createCategory(fast_foody);
		
		Category puby_i_bary = new Category();
		puby_i_bary.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		puby_i_bary.setCategoryId(0);
		puby_i_bary.setCategoryName("Puby i bary");
		puby_i_bary.setMasterCategory(masterCategory);
		categoriesService.createCategory(puby_i_bary);
		
		Category kawiarnie = new Category();
		kawiarnie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		kawiarnie.setCategoryId(0);
		kawiarnie.setCategoryName("Kawiarnie");
		kawiarnie.setMasterCategory(masterCategory);
		categoriesService.createCategory(kawiarnie);
		
		Category cukiernie = new Category();
		cukiernie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		cukiernie.setCategoryId(0);
		cukiernie.setCategoryName("Cukiernie");
		cukiernie.setMasterCategory(masterCategory);
		categoriesService.createCategory(cukiernie);
		
		Category lodziarnie = new Category();
		lodziarnie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		lodziarnie.setCategoryId(0);
		lodziarnie.setCategoryName("Lodziarnie");
		lodziarnie.setMasterCategory(masterCategory);
		categoriesService.createCategory(lodziarnie);
		
		Category plenerowe = new Category();
		plenerowe.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		plenerowe.setCategoryId(0);
		plenerowe.setCategoryName("Plenerowe");
		plenerowe.setMasterCategory(masterCategory);
		categoriesService.createCategory(plenerowe);
		
		/*Sporty: wspinaczka, pi³ka no¿na, koszykówka, siatkówka, tenis ziemny, tenis sto³owy, nurkowanie, rowery, bieganie, kajaki, ¿eglowanie, chodzenie*/
		masterCategory = categoriesService.getMastercategoryByName(sport.getName());
		
		Category wspinaczka = new Category();
		wspinaczka.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		wspinaczka.setCategoryId(0);
		wspinaczka.setCategoryName("Wspinaczka");
		wspinaczka.setMasterCategory(masterCategory);
		categoriesService.createCategory(wspinaczka);
		
		Category pilka_nozna = new Category();
		pilka_nozna.setCategoryIconPath("/resources/categories/images/pilka.png");
		pilka_nozna.setCategoryId(0);
		pilka_nozna.setCategoryName("Pi³ka no¿na");
		pilka_nozna.setMasterCategory(masterCategory);
		categoriesService.createCategory(pilka_nozna);
		
		Category koszykowka = new Category();
		koszykowka.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		koszykowka.setCategoryId(0);
		koszykowka.setCategoryName("Koszykówka");
		koszykowka.setMasterCategory(masterCategory);
		categoriesService.createCategory(koszykowka);
		
		Category siatkowka = new Category();
		siatkowka.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		siatkowka.setCategoryId(0);
		siatkowka.setCategoryName("Siatkówka");
		siatkowka.setMasterCategory(masterCategory);
		categoriesService.createCategory(siatkowka);
		
		Category tenis = new Category();
		tenis.setCategoryIconPath("/resources/categories/images/pilka_tenisowa.png");
		tenis.setCategoryId(0);
		tenis.setCategoryName("Tenis ziemny");
		tenis.setMasterCategory(masterCategory);
		categoriesService.createCategory(tenis);
		
		Category tenis_stolowy = new Category();
		tenis_stolowy.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		tenis_stolowy.setCategoryId(0);
		tenis_stolowy.setCategoryName("Tenis sto³owy");
		tenis_stolowy.setMasterCategory(masterCategory);
		categoriesService.createCategory(tenis_stolowy);
		
		Category nurkowanie = new Category();
		nurkowanie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		nurkowanie.setCategoryId(0);
		nurkowanie.setCategoryName("Nurkowanie");
		nurkowanie.setMasterCategory(masterCategory);
		categoriesService.createCategory(nurkowanie);
		
		Category rowery = new Category();
		rowery.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		rowery.setCategoryId(0);
		rowery.setCategoryName("Rowery");
		rowery.setMasterCategory(masterCategory);
		categoriesService.createCategory(rowery);
		
		Category bieganie = new Category();
		bieganie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		bieganie.setCategoryId(0);
		bieganie.setCategoryName("Bieganie");
		bieganie.setMasterCategory(masterCategory);
		categoriesService.createCategory(bieganie);
		
		Category kajaki = new Category();
		kajaki.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		kajaki.setCategoryId(0);
		kajaki.setCategoryName("Kajaki");
		kajaki.setMasterCategory(masterCategory);
		categoriesService.createCategory(kajaki);
		
		Category zeglowanie = new Category();
		zeglowanie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		zeglowanie.setCategoryId(0);
		zeglowanie.setCategoryName("¯eglowanie");
		zeglowanie.setMasterCategory(masterCategory);
		categoriesService.createCategory(zeglowanie);
		
		Category chodzenie = new Category();
		chodzenie.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		chodzenie.setCategoryId(0);
		chodzenie.setCategoryName("Chodzenie");
		chodzenie.setMasterCategory(masterCategory);
		categoriesService.createCategory(chodzenie);
		
		/*Podró¿e i turystyka: nad morze, nad jeziora, w góry, turystyka miejska*/
		masterCategory = categoriesService.getMastercategoryByName(podroze_i_turystyka.getName());
		
		Category nad_morze = new Category();
		nad_morze.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		nad_morze.setCategoryId(0);
		nad_morze.setCategoryName("Morze");
		nad_morze.setMasterCategory(masterCategory);
		categoriesService.createCategory(nad_morze);
		
		Category nad_jeziora = new Category();
		nad_jeziora.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		nad_jeziora.setCategoryId(0);
		nad_jeziora.setCategoryName("Jeziora");
		nad_jeziora.setMasterCategory(masterCategory);
		categoriesService.createCategory(nad_jeziora);
		
		Category w_gory = new Category();
		w_gory.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		w_gory.setCategoryId(0);
		w_gory.setCategoryName("Góry");
		w_gory.setMasterCategory(masterCategory);
		categoriesService.createCategory(w_gory);
		
		Category turystyka_miejska = new Category();
		turystyka_miejska.setCategoryIconPath("/resources/categories/images/kwiatek.png");
		turystyka_miejska.setCategoryId(0);
		turystyka_miejska.setCategoryName("Turystyka miejska");
		turystyka_miejska.setMasterCategory(masterCategory);
		categoriesService.createCategory(turystyka_miejska);
		/****************************************************/
		
		/***********************events***********************/
		Category cat = categoriesService.getCategoryByName("Pi³ka no¿na");
		Set<Category> category = new HashSet<Category>();
		category.add(cat);
		Integer catId = cat.getCategoryId();
		
		EventForm pilkaNaMiasteczku = new EventForm();
		pilkaNaMiasteczku.setCategoriesSelected(new String[] {catId.toString()});
		pilkaNaMiasteczku.setEventId(0);
		pilkaNaMiasteczku.setCity("Kraków");
		pilkaNaMiasteczku.setConfirmParticipants(true);
		pilkaNaMiasteczku.setCountry("Poland");
		pilkaNaMiasteczku.setStreet("Piastowska");
		pilkaNaMiasteczku.setNumber_of_participants(5);
		pilkaNaMiasteczku.setHeader("pilka na miasteczku studenckim");
		pilkaNaMiasteczku.setDescryption("kompletujemy squad, 5 osob juz jest, szukamy jeszcze pieciu");
		pilkaNaMiasteczku.setDate("31-08-2014 18:00");
		pilkaNaMiasteczku.setOrganizer(matrom);
		categoriesService.registerEvent(
				pilkaNaMiasteczku);
		
		EventForm pilkaGdzieIndziej = new EventForm();
		pilkaGdzieIndziej.setCategoriesSelected(new String[] {catId.toString()});
		pilkaGdzieIndziej.setEventId(0);
		pilkaGdzieIndziej.setCity("Kraków");
		pilkaGdzieIndziej.setConfirmParticipants(true);
		pilkaGdzieIndziej.setCountry("Poland");
		pilkaGdzieIndziej.setStreet("Piastowska");
		pilkaGdzieIndziej.setNumber_of_participants(5);
		pilkaGdzieIndziej.setHeader("pilka nie wiem gdzie");
		pilkaGdzieIndziej.setDescryption("hohohohoho");
		pilkaGdzieIndziej.setDate("25-08-2014 15:00");
		pilkaGdzieIndziej.setOrganizer(bodziek);
		categoriesService.registerEvent(
				pilkaGdzieIndziej);
		/****************************************************/
		
		return null;
	}
	
	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public ModelAndView run( ModelMap model,
			Principal principal) {
		
        
		Scheduler scheduler = (Scheduler) applicationContext.getBean("scheduler");

        //get the task bean
        MyTask myTask = (MyTask) applicationContext.getBean("myTask");
        
        

        try {
        // create JOB
        /*MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        
        
        jobDetail.setTargetObject(myTask);
        jobDetail.setTargetMethod("performAction");
        jobDetail.setName("MyJobDetail");
        jobDetail.setConcurrent(true);
        jobDetail.setArguments(new String[]{"bodziek", "agr2"});
        jobDetail.afterPropertiesSet();
        
        MethodInvokingJobDetailFactoryBean jobDetail2 = new MethodInvokingJobDetailFactoryBean();
        jobDetail2.setTargetObject(myTask);
        jobDetail2.setTargetMethod("performAction");
        jobDetail2.setName("MyJobDetail2");
        jobDetail2.setConcurrent(true);
        jobDetail2.setArguments(new String[]{"matrom", "agr4"});
        jobDetail2.afterPropertiesSet();
            
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy kk:mm:ss");
        
    	String dateInString = "13-09-2014 21:48:30";
    	Date date = new Date();
    	date = sdf.parse(dateInString);
        System.out.println(date);
    	
    	SimpleTriggerBean trigger = new SimpleTriggerBean();
        trigger.setBeanName("MyTrigger");
        
        trigger.setJobDetail(jobDetail.getObject());
        trigger.setStartTime(date);
        trigger.afterPropertiesSet();
        trigger.setRepeatCount(0);
        trigger.setRepeatInterval(0);
        

        dateInString = "13-09-2014 21:48:40";
    	Date date2 = new Date();
    	date2 = sdf.parse(dateInString);
        
    	SimpleTriggerBean trigger2 = new SimpleTriggerBean();
        trigger2.setBeanName("MyTrigger2");
        trigger2.setJobDetail( jobDetail2.getObject());
        trigger2.setStartTime(date2);
        trigger2.afterPropertiesSet();
        trigger2.setRepeatCount(0);
        trigger2.setRepeatInterval(0);*/
        	
        	
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("username", "bodziek");
        	
        JobDetailBean jobDetail = new JobDetailBean();
        jobDetail.setBeanName("MyJobDetail");
        jobDetail.setName("MyJobDetail");
        jobDetail.setJobClass(myTask.getClass());
        jobDetail.setGroup(null);
        jobDetail.setJobDataMap(jobDataMap);
        jobDetail.afterPropertiesSet();
        
        JobDataMap jobDataMap2 = new JobDataMap();
        jobDataMap2.put("username", "matrom");
        
        JobDetailBean jobDetail2 = new JobDetailBean();	
        jobDetail2.setName("MyJobDetail2");
        jobDetail2.setJobClass(myTask.getClass());
        jobDetail2.setJobDataMap(jobDataMap2);
        jobDetail2.afterPropertiesSet();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy kk:mm:ss");
        
    	String dateInString = "23-09-2014 18:18:30";
    	Date date = new Date();
    	date = sdf.parse(dateInString);
        System.out.println(date);
    	
    	SimpleTriggerBean trigger = new SimpleTriggerBean();
        trigger.setBeanName("MyTrigger");
        
        trigger.setJobDetail(jobDetail);
        trigger.setStartTime(date);
        trigger.afterPropertiesSet();
        trigger.setRepeatCount(0);
        trigger.setRepeatInterval(0);
        

        dateInString = "23-09-2014 18:18:45";
    	Date date2 = new Date();
    	date2 = sdf.parse(dateInString);
        
    	SimpleTriggerBean trigger2 = new SimpleTriggerBean();
        trigger2.setBeanName("MyTrigger2");
        trigger2.setJobDetail(jobDetail2);
        trigger2.setStartTime(date2);
        trigger2.afterPropertiesSet();
        trigger2.setRepeatCount(0);
        trigger2.setRepeatInterval(0);
        
        scheduler.start();
        scheduler.getContext().put("aService", userService);
        
       // 
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail2, trigger2);

        // Start Scheduler        
        //context.close();
        
        } catch (Exception e) {                      
            e.printStackTrace();
        } 
		
		return null;
	}
	
	@RequestMapping(value = "/run2", method = RequestMethod.GET)
	public ModelAndView run2( ModelMap model,
			Principal principal) {
		Scheduler scheduler = (Scheduler) applicationContext.getBean("scheduler");

        //get the task bean
        MyTask myTask = (MyTask) applicationContext.getBean("myTask");
        

        try {
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("username", "bodziek");
        	
        JobDetailBean jobDetail = new JobDetailBean();
        jobDetail.setBeanName("MyJobDetail3");
        jobDetail.setName("MyJobDetail3");
        jobDetail.setJobClass(myTask.getClass());
        jobDetail.setGroup(null);
        jobDetail.setJobDataMap(jobDataMap);
        jobDetail.afterPropertiesSet();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy kk:mm:ss");
        
    	String dateInString = "19-09-2014 20:11:00";
    	Date date = new Date();
    	date = sdf.parse(dateInString);
        System.out.println(date);
    	
    	SimpleTriggerBean trigger = new SimpleTriggerBean();
        trigger.setBeanName("MyTrigger");
        
        trigger.setJobDetail(jobDetail);
        trigger.setStartTime(date);
        trigger.afterPropertiesSet();
        trigger.setRepeatCount(0);
        trigger.setRepeatInterval(0);
        
        scheduler.getContext().put("aService", userService);
       // 
        scheduler.scheduleJob(jobDetail, trigger);

        // Start Scheduler        
        //context.close();
        
        } catch (Exception e) {                      
            e.printStackTrace();
        } 
		
		return null;
	}
	
	
	@RequestMapping(value = "/startScheduler", method = RequestMethod.GET)
	public ModelAndView startScheduler( ModelMap model,
			Principal principal) {
		
        
		Scheduler scheduler = (Scheduler) applicationContext.getBean("scheduler");
		try {
			scheduler.start();
			scheduler.getContext().put(EventTask.userServiceKey, userService);
			scheduler.getContext().put(EventTask.eventServiceKey, eventService);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
      
		return null;
	}

}
