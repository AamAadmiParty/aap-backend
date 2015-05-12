package com.next.aap.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.controller.validators.UserProfileValidator;
import com.next.aap.web.dto.InterestDto;
import com.next.aap.web.dto.InterestGroupDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserInterestDto;
import com.next.aap.web.dto.VolunteerDto;
import com.next.aap.web.util.ContentDonwloadUtil;

@Controller
public class RegisterController extends AppBaseController {

    @Autowired
    private ContentDonwloadUtil contentDonwloadUtil;

    @Autowired
    private UserProfileValidator userProfileValidator;

    @InitBinder("user")
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        // binder.setValidator(userProfileValidator);
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public ModelAndView getAllCountries(ModelAndView mv, HttpServletRequest httpServletRequest) {
        UserDto user = getLoggedInUserFromSesion(httpServletRequest);
        if (user == null) {
            user = new UserDto();
        }

        mv = new ModelAndView(design + "/register", "user", user);
        mv = preparePage(httpServletRequest, user, mv);

        return mv;
    }

    private void loadVolunteerDetails(ModelAndView mv, UserDto user) {
        try {
            VolunteerDto selectedVolunteer = null;
            System.out.println("Loading Volunteer Details of User " + user.getId());
            Set<Long> selectedInterestMap = new HashSet<Long>();
            if (user != null) {
                selectedVolunteer = aapService.getVolunteerDataForUser(user.getId());
                System.out.println("selectedVolunteer :  " + selectedVolunteer);
                List<InterestDto> userInterests = aapService.getuserInterests(user.getId());
                System.out.println("userInterests :  " + userInterests);
                if (userInterests != null && userInterests.size() > 0) {
                    for (InterestDto oneInterestDto : userInterests) {
                        System.out.println("Adding : " + oneInterestDto.getId());
                        selectedInterestMap.add(oneInterestDto.getId());
                    }
                }
            }

            List<InterestGroupDto> interestGroups = aapService.getAllVolunterInterests();
            System.out.println("${interestGroups} = " + interestGroups);
            mv.getModel().put("interestGroups", interestGroups);
            List<UserInterestDto> userInterestDtos = new ArrayList<UserInterestDto>();
            for (InterestGroupDto oneInterestGroupDto : interestGroups) {
                for (InterestDto oneInterestDto : oneInterestGroupDto.getInterestDtos()) {
                    userInterestDtos.add(new UserInterestDto(oneInterestDto, selectedInterestMap.contains(oneInterestDto.getId())));
                }
            }

            if (selectedVolunteer == null) {
                selectedVolunteer = new VolunteerDto();
            }
            user.setVolunteerDto(selectedVolunteer);
            user.setUserInterestDtos(userInterestDtos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private ModelAndView preparePage(HttpServletRequest httpServletRequest, UserDto user, ModelAndView mv) {
        try {
            addNriCountriesIntoModel(mv);
            addIndianStatesIntoModel(mv);
            if (user.getStateLivingId() != null && user.getStateLivingId() > 0) {
                addDistrictIntoModel(mv, user.getStateLivingId(), "livingDistricts");
                addPcIntoModel(mv, user.getStateLivingId(), "livingPcs");
            }
            if (user.getStateVotingId() != null && user.getStateVotingId() > 0) {
                addDistrictIntoModel(mv, user.getStateVotingId(), "votingDistricts");
                addPcIntoModel(mv, user.getStateVotingId(), "votingPcs");
            }
            if (user.getDistrictLivingId() != null && user.getDistrictLivingId() > 0) {
                addAcIntoModel(mv, user.getDistrictLivingId(), "livingAcs");
            }
            if (user.getDistrictVotingId() != null && user.getDistrictVotingId() > 0) {
                addAcIntoModel(mv, user.getDistrictVotingId(), "votingAcs");
            }
            if (user.getNriCountryId() != null && user.getNriCountryId() > 0) {
                addNriCountryRegionsIntoModel(mv, user.getNriCountryId());
            }
            if (user.getNriCountryRegionId() != null && user.getNriCountryRegionId() > 0) {
                addNriCountryRegionAreasIntoModel(mv, user.getNriCountryRegionId());
            }
            System.out.println("loadVolunteerDetails");
            loadVolunteerDetails(mv, user);
            addGenericValuesInModel(httpServletRequest, mv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    private void printVolunteerDetail(UserDto user) {
        System.out.println("Volunteer : " + user.getVolunteerDto());
        System.out.println("Total : " + user.getUserInterestDtos().size());
        for (UserInterestDto oneUserInterestDto : user.getUserInterestDtos()) {
            if (oneUserInterestDto.isSelected()) {
                System.out.println("selected : " + oneUserInterestDto.getId() + " , " + oneUserInterestDto.getDescription());
            }
        }
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public ModelAndView saveUserProfile(@ModelAttribute("user") UserDto user, BindingResult result, ModelAndView mv, HttpServletRequest httpServletRequest) {
        printVolunteerDetail(user);
        mv = new ModelAndView(design + "/register", "user", user);
        if (result.hasErrors()) {
            System.out.println("Has Errors " + result);
            preparePage(httpServletRequest, user, mv);
            return mv;
        }
        System.out.println("No Erros found [" + user.getName() + "," + httpServletRequest.getParameter("name") + "]");
        if (!user.isNri()) {
            if (user.getStateLivingId() == null || user.getStateLivingId() == 0) {
                addErrorInModel(mv, "Please select State where you are living currently");
            }
            if (user.getDistrictLivingId() == null || user.getDistrictLivingId() == 0) {
                addErrorInModel(mv, "Please select District where you are living currently");
            }
            if (user.getAssemblyConstituencyLivingId() == null || user.getAssemblyConstituencyLivingId() == 0) {
                addErrorInModel(mv, "Please select Assembly Constituency where you are living currently");
            }
            if (user.getParliamentConstituencyLivingId() == null || user.getParliamentConstituencyLivingId() == 0) {
                addErrorInModel(mv, "Please select Parliament Constituency where you are living currently");
            }
        }

        if (user.getStateVotingId() == null || user.getStateVotingId() == 0) {
            addErrorInModel(mv, "Please select State where you are registered as Voter");
        }
        if (user.getDistrictVotingId() == null || user.getDistrictVotingId() == 0) {
            addErrorInModel(mv, "Please select District where you are registered as Voter");
        }
        if (user.getAssemblyConstituencyVotingId() == null || user.getAssemblyConstituencyVotingId() == 0) {
            addErrorInModel(mv, "Please select Assembly Constituency where you registered as Voter");
        }
        if (user.getParliamentConstituencyVotingId() == null || user.getParliamentConstituencyVotingId() == 0) {
            addErrorInModel(mv, "Please select Parliament Constituency where you registered as Voter");
        }
        if (user.isNri()) {
            if ((user.getNriCountryId() == null || user.getNriCountryId() == 0)) {
                addErrorInModel(mv, "Please select Country where you Live");
            }
            if (user.isMember() && StringUtil.isEmpty(user.getPassportNumber())) {
                addErrorInModel(mv, "Please enter passport number. Its Required for NRIs to become member.");
            }
        }
        if (user.getDateOfBirth() == null) {
            addErrorInModel(mv, "Please enter your Date of Birth");
        }
        if (StringUtil.isEmptyOrWhitespace(user.getName())) {
            addErrorInModel(mv, "Please enter your full name");
        }
        if (isValidInput(mv)) {
            try {
                System.out.println("saving User " + user);
                user.setCountryCode("91");
                user = aapService.registerUser(user);

                System.out.println("saving Volunteer Detail");
                List<Long> selectedInterests = new ArrayList<Long>();
                if (user.getUserInterestDtos() != null) {
                    for (UserInterestDto oneInterestDto : user.getUserInterestDtos()) {
                        if (oneInterestDto.isSelected()) {
                            selectedInterests.add(oneInterestDto.getId());
                        }
                    }
                }
                if (user.isVolunteer()) {
                    VolunteerDto selectedVolunteer = user.getVolunteerDto();
                    selectedVolunteer.setInfoRecordedAt("Self Service Portal");
                    selectedVolunteer.setInfoRecordedBy("Self");
                    selectedVolunteer.setUserId(user.getId());
                    selectedVolunteer = aapService.saveVolunteerDetails(selectedVolunteer, selectedInterests);
                    user.setVolunteerDto(selectedVolunteer);
                    System.out.println("Volunteer Saved");
                }
                mv.getModel().put("message", "Profile Saved succesfully");
                mv.setViewName(design + "/registerconfirmation");
            } catch (Exception ex) {
                ex.printStackTrace();
                addErrorInModel(mv, ex.getMessage());
            }
        }
        System.out.println("Preparing Page");

        mv = preparePage(httpServletRequest, user, mv);
        return mv;
    }

}
