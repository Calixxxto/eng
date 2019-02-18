package account.config;

import account.security.UserDetailsServiceImpl;
import account.service.BillingSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@ComponentScan(basePackages = {"account.security"})
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Lazy
    private BillingSessionService billingSessionService;

    private String[] urlsWithoutRolesGet() {
        return new String[]{
                "/api/location",
                "/api/location/search",
                "/api/address/houses/**",
                "/api/address/msthouses/**",
                "/api/address/exthouses/**",
                "/api/repair/housectgs",
                "/api/repair/search/findByType",
                "/api/reference",
                "/api/flat/**",
                "/api/root/appinfo",
                "/repository/flats/search/findByHouseIdAndFlatNumContaining",
                "/repository/flats/search/findByAccount",
                "/repository/propertytypes",
                "/api/report/houseAccounts",
                "/system/client/log",
                "/api/user/**",
                "/repository/userpositions/search/findByActiveTrueOrderByName",
                "/repository/userdepartments/search/findByActiveTrueOrderByName",
                "/api/users/findByNameAndAddress",
                "/repository/repairWorkKind",
                "/repository/userDepartmentsGlobal/search/findByRepairWorkShowTrueOrderByName",
                "/api/repairHouse",
                "/repository/liftRepairTypes",
                "/repository/repairWorkNeeds",
                "/api/subsidy/status",
                "/api/subsidy/list",
                "/api/subsidy/view/**",
                "/api/subsidy/subsidyRepairTypes",
                "/api/subsidy/getAuctionContractDocuments",
                "/api/edoc/**",
                "/api/fond/info",
                "/api/fdoc/**",
                "/api/subsidy/getStateForDepartment",
                "/api/subsidy/getFilePackageDocs",
                "/api/subsidy/subsidyAgreeComments",
                "/api/subsidy/package/docs",
                "/repository/auctionLog/**",
                "/repository/auctionContractLog/**",
                "/repository/subsidyLog/**",
                "/api/report/FormKC_2",
                "/api/report/FormKC_3",
                "/api/report/FormKC_14",
                "/api/subsidy/subsidyDocByHouses",
                "/api/house/**/getHistoryRegProgram",
                "/api/house/**/getHouseTotals",
                "/api/protocol/list",
                "/api/repairWorkKind/list",
                "/api/auctionplan/getArchiveFromHouseFiles",
                "/api/auctionplan/getArchiveDocs",
                "/api/auctionplan/getArchiveFileRegistry",
                "/repository/lifts/search/findAllByLiftNumberStartsWithIgnoreCase",
                "/api/report/director/createTable",
                "/api/report/reportsKPAllInExcel",
                "/system/admin/cache/reset",
                "/repository/repairplans/search/findRepairPlanByDocumentStatus",
                "/api/user_works",
                "/api/report/weeklyReportsInExcel",
                "/api/repair/plan/getRepairPlansInEffect",
                "/repository/weeklyReportsTypes",
                "/api/auctionplan/getWorksStartTypes",
                "/api/auctionplan/getFinancingTypes",
                "/api/auctionplan/getDocTypeList",
                "/api/report/director/createDirectorsReport",
                "/api/auctionPayment/getAuctionPaymentsByAuctionContractRepairTypeId",
                "/repository/auctionPaymentType",
                "/repository/auctionPaymentSource",
                "/repository/auctionPlanRefType",
                "/repository/streetsReg/search/findAllByExtDistrictREG_Id",
                "/api/repairHouseWork/getRepairHouseWorks",
                "/repository/templateGroupRepository",
                "/repository/commissionMemberPositionRepository",
                "/repository/reasonNotAllowedTypeRepository",
                "/repository/applicationForAuctionProvidedDocTypeRepository",
                "/api/report/kpConsolidated",
                "/repository/exp1C_Repository",
                "/api/lifts/list",
                "/api/findByLift/*",
                "/api/getLiftProducerEquipments/list",
                "/api/getLiftMineTypes/list",
                "/api/getLiftModeWorkTypes/list",
                "/api/getLiftTypes/list",
                "/api/report/rwLastKp",
                "/api/contractorTypesTaxSystem/list"
        };
    }

    /**
     * @return reference book links. Loaded at browser start.
     */
    private String[] urlsReferences() {
        return new String[]{
                "/repository/repairstatuses/search/**",
                // KAP-692
                "/repository/districts",
                "/repository/repairtypes",
                "/repository/districtsreg",
                "/repository/teielements",
                "/repository/subsidyStatus",
        };
    }

    private String[] urlsUserDeputyGDPretenseAuctionKapremontOpkrProjectGatiEstimatePlaneconOfapOknOperatorCommunicationRole() {
        return new String[]{
                "/repository/housectgs/**",
                "/repository/stdtariffs/search/findByHouseCategoryId",
                "/api/billing/stdtariffs/search/findByHouseCategoryId",
                "/repository/stdtariffs/**",
                "/repository/users/**",
                "/api/users",
                "/api/report/worksProgressInfo",
                "/api/report/pretenseInfo",
                "/api/regplans/countByRegPlanInHistory",
                "/repository/repairplans/search/findRepairPlanByDocumentStatusAndYear",
                "/repository/regplans/search/findRegPlanByDocumentStatus",
                "/api/repair/plan/moveToActive",
                "/api/reg/plan/**",
                "/api/report/auctionplan/**",
                "/api/report/auctionPlanStandardDocument/**",
                "/api/report/auctioncontract/**",
                "/repository/documentstatuses/search/findByActiveTrueOrderByName",
                "/api/doc/**", // simple docs, which are not associated with entity
                "/repository/houseDecisions/search/findByEffectiveDateAndControversialIsFalseAndActiveIsTrue",
        };
    }

    private String[] urlsDeputyGDOperatorKapremontOpkrProjectGatiEstimatePlaneconOfapOknRolePatch() {
        return new String[]{
                "/repository/regplans",
                "/repository/regplans/**",
                "/api/reg/plan/**",
                "/repository/repairplans",
                "/repository/repairplans/**",
                "/api/repair/plan/**"
        };
    }

    private String[] urlsUserDeputyGDRole() {
        return new String[]{
                // report constructor services:
                "/repository/reportfilters/search/findByReportSourceId",
                "/repository/reportsources",
                "/repository/reportcolumns/search/findByReportSourceId",
                "/repository/reportinstances",
                "/repository/reportinstances/**",
                "/api/report/constructor/generate",
                "/api/report/constructor/instance/*"
        };
    }

    private String[] urlsDeputyGDRolePost() {
        return new String[]{
                "/api/report/constructor/instance",
                "/api/reg/plan",
                "/api/edoc/regPlan/**",
                "/api/repairplan",
                "/api/edoc/repairPlan/**"
        };
    }

    private String[] urlsDeputyGDRoleDelete() {
        return new String[]{
                "/repository/reportinstances/*",
                "/repository/regplans",
                "/repository/regplans/**",
                "/api/edoc/regPlan/**",
                "/repository/repairplans",
                "/repository/repairplans/**",
                "/api/edoc/repairPlan/**"
        };
    }

    private String[] urlsUserDeputyGDOperatorCommunicationPretenseRoles() {
        return new String[]{
                "/repository/decisiontypes/search/findByVisibleTrue",
                "/repository/banks/**",
                "/repository/decisions/search/findByHouseIdAndActiveTrue",
                "/repository/decisions/**",
                "/repository/houseDecisions/search/findById",
                "/repository/specAccounts/search/**",
                "/repository/extsystems/search/countByServiceCodesContaining",
                "/api/billing/summary/groupByDecision",
                "/repository/housemgmttypes",
                "/api/decision/getMstHouseDecisionTypes",
        };
    }

    private String[] urlsUserDeputyGDOperatorCommunicationPretenseCallcenterRoles() {
        return new String[]{
                "/api/billing/bills/*"
        };
    }

    private String[] urlsOperatorCommunicationBankPost() {
        return new String[]{
                "/repository/decisions",
                "/api/decision",
                "/api/flat/**",
                "/api/edoc/house/**",
                "/api/edoc/flat/**"
        };
    }


    private String[] urlsAdminRolePostMethod() {
        return new String[]{
                "/api/address/house/updateBillingLocked"

        };
    }

    private String[] urlsOperatorCommunicationRoleDeleteMethod() {
        return new String[]{
                "/api/edoc/house/**"
        };
    }

    private String[] urlsOperatorCommunicationBankRoleDeleteMethod() {
        return new String[]{
                "/api/edoc/flat/**"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknOperatorCommunicationBankDelete() {
        return new String[]{
                "/api/edoc/mstHouse/**"
        };
    }

    private String[] urlsBankRoleDelete() {
        return new String[]{
                "/api/payment/session/**",
                "/api/payment/**",
                // KAP-1297
                "/api/requisite",
                "/repository/flatowners/*",
                "/api/edoc/flatOwner/*/*",
                "/repository/refinancerates/*"
        };
    }

    private String[] urlsBankRolePatch() {
        return new String[]{
                "/api/payment/session/**",
                "/api/payment/**",
                // KAP-1297
                "/api/requisite",
                "/repository/flatowners/*"
        };
    }

    private String[] urlsBankRolePost() {
        return new String[]{
                "/repository/payments/**",
                "/api/payment/session",
                "/api/payment/reverse",
                // KAP-1297
                "/api/requisite",
                "/repository/flatowners",
                "/api/edoc/flatOwner/*",
                "/api/billing/flatOwner/*/bill",
                "/api/refinance/rate/**",
                "/api/address/house/updateBillingUnfit",
                "/repository/exp1C_Repository"

        };
    }

    private String[] urlsBankRoleGet() {
        return new String[]{
                "/api/external/systems/findByExportAgentTrue",
                "/api/register/status",
                "/api/register/start",
                "/api/register",
                "/api/report/paymentSession",
                "/api/billing/owner/preview",
                "/api/billing/owner/pay",
                "/api/refinance/rate",
                "/api/report/flatOwners"
        };
    }

    private String[] urlsUserDeputyGDBankPretenseCommunicationRoleGet() {
        return new String[]{
                "/api/payment/session/search/findByStatusAndServiceCodeAndExtSystemIdAndPaymentDateBetween",
                "/repository/extsystems",
                "/api/billing/session/status",
                "/repository/billingsessions",
                "/api/payment/session/status",
                "/repository/mgmtcompanyspecaccounts",
                "/repository/flatowners",
                "/repository/flatowners/*",
                "/repository/flatowners/search/findByInn",
                "/repository/flatowners/search/findByInnContains",
                "/repository/flatowners/search/findByInnContainsAndFullNameContainsIgnoreCase",
                "/api/external/systems/serviceCodes",
                "/api/billing/flatOwner/*/bill",
                "/repository/ownedflats/search/findByFlatOwnerId"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknUserDeputyGDBankOperatorCommunicationPretenseBuildControlAuctionRoleGet() {
        return new String[]{
                "/api/dashboard",
                "/api/repair/**",
                "/repository/repairdecisions/**",
                "/repository/repairdecisions",
                "/repository/repairoffers/**",
                "/repository/repairoffers",
                "/repository/repairlongs/**",
                "/repository/repairlongs",
                "/repository/repairtypes/**",
                "/repository/repairstatuses/**",
                "/repository/repairstatuses",
                "/repository/repairstatushistory/**",
                "/repository/repairstatushistory",
                "/repository/lots/**",
                "/repository/lots",
                "/repository/contractors/**",
                "/repository/contractors",
                "/repository/repairplans/**",
                "/repository/repairplans",
                "/api/report/worksByYear",
                "/api/repair/work/types",
                "/api/report/auctionForm4",
                "/api/report/auctionForm3",
                "/api/report/auctionForm3Contract"

        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknUserDeputyGDBankOperatorCommunicationPretenseCallcenterAuctionRoleGet() {
        return new String[]{
                // KAP-2568
                "/api/fee",
                "/api/fee/countFeeCancelled",
                "/repository/fees/search/findByFlatId",
                "/repository/penalties/search/findByFlatId",
                "/repository/payments/**",
                "/repository/bills/search/findByFlatId",
                "/api/billing/fee/sumByFlatIdDateRange",
                "/api/billing/penalty/sumByFlatIdDateRange",
                "/api/billing/penalty/sumFondByFlatIdDateRange",
                "/api/billing/payment/sumByFlatIdDateRange",
                "/repository/fees/search/findByFlatIdAndRange",
                "/repository/penalties/search/findByFlatIdAndRange",
                "/api/report/letterDebt",
                "/repository/letters",
                "/api/billing/letter",
                "/api/flat/generateLetter",
                "/repository/regplans",
                "/repository/regplans/**",
                "/repository/regcomments",
                "/repository/reghistories",
                "/api/contractor/list",
                "/api/auctionplan/numberslist"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknUserDeputyGDBankOperatorCommunicationPretenseRoleGet() {
        return new String[]{
                // KAP-810
                "/api/requisite",
                "/repository/housemgmttypes",
                "/api/billing/summary/**",
                "/api/billing/collect/**",
                "/api/report/districtCollect",
                "/repository/extsystems/search/**",
                "/api/report/paymentInfo",
                "/api/report/dailyPayment",
                "/api/report/paymentInfo/status",
                "/repository/decisiontypes",
                "/api/report/houseDecisions",
                "/api/report/houseFees",
                "/api/report/housePayments",
                "/api/report/regPaymentInfo",
                "/api/report/specPaymentInfo",
                "/api/report/specAccNotification",
                "/api/report/workSummary",
                "/api/report/worksPaymentInfo",
                "/api/report/repairOffer",
                "/api/report/houseAccounts",
                "/repository/tei*/**",
                "/api/penalties/penaltiesTrans"
        };
    }

    private String[] urlsOperatorBankUserDeputyGDCommunicationPretenseGet() {
        return new String[]{
                "/api/billing/summary/groupByYearMonth",
                "/api/report/billingSummaryGroupByYearMonth",
                "/api/report/paymentSessions"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknBuildControlOperatorAuctionRolePostPutPatchDelete() {
        return new String[]{
                "/api/repair/**",
                "/repository/repairlongs/**",
                "/repository/repairlongs",
                "/repository/repairtypes/**",
                "/repository/repairtypes",
                "/repository/repairstatuses/**",
                "/repository/repairstatuses",
                "/repository/repairstatushistory/**",
                "/repository/repairstatushistory",
                "/repository/lots/**",
                "/repository/lots",
                "/repository/contractors/**",
                "/repository/contractors",
                "/repository/repairdecisions/**",
                "/repository/repairdecisions",
                "/repository/repairoffers/**",
                "/repository/repairoffers",
                "/repository/repairplans/**",
                "/repository/repairplans",
                "/api/report/worksPaymentInfo",
                "/api/edoc/repairDecision/**",
                "/api/edoc/repairWork/**",
                "/repository/tei*/**",
                "/api/edoc/tei/**",
                "/api/edoc/repairOffer/**",
                "/api/kgiop/doc/**",
                "/api/house/okn/**",
                "/api/repair/work/getRepairWorkByHouseId",
                "/api/repair/work/updatePercentComplete",
                "/repository/kgioprepworkdoctypes/search/findByNameNotNullOrderByName",
                "/repository/okncategories/search/findByNameNotNullOrderByName",
                "/repository/oknreasondoctypes/search/findByNameNotNullOrderByName",
                "/repository/okntypes/search/findByNameNotNullOrderByName",
                "/api/kgiop/doc/findByHouseId",
                "/api/edoc/kgiopDoc/**",
                "/api/edoc/oknHouse/**",
                "/api/house/okn/findByHouseId",
                "/repository/userDepartmentsGlobal/search/findByActiveTrueAndGroupByIdOrderByNameShort",
                "/api/repairwork/permissionglobal"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknBuildControlRoleGet() {
        return new String[]{
                "/api/kgiop/doc/**",
                "/api/house/okn/**",
                "/api/repair/work/getRepairWorkByHouseId",
                "/api/repair/work/updatePercentComplete",
                "/repository/kgioprepworkdoctypes/search/findByNameNotNullOrderByName",
                "/repository/okncategories/search/findByNameNotNullOrderByName",
                "/repository/oknreasondoctypes/search/findByNameNotNullOrderByName",
                "/repository/okntypes/search/findByNameNotNullOrderByName",
        };
    }

    private String[] urlsUserDeputyGDBankOperatorCommunicationPretenseRoleGet() {
        return new String[]{
                "/api/billing/specBills/collect/groupByHouse",
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknOperatorCommunicationPost() {
        return new String[]{
                "/api/address/house/**/updateManagement"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknOperatorCommunicationBankPost() {
        return new String[]{
                "/api/edoc/mstHouse/**"
        };
    }

    /**
     * Returns URLs to be restricted for POST,PUT,PATCH, DELETE when billing is in progress
     * KAP-863
     *
     * @return
     */
    private String[] urlsRestrictedOnBilling() {
        return new String[]{
                "/api/flat/updateSqrBase",
                "/api/flat/updateFlatKadNum",
                "/api/flat/updateFlatNum",
                "/api/penalties/cancelPenalties",
                "/api/penalties/cancelPenaltiesHouse",
                "/api/flat/split",
                "/api/flat/close",
                "/api/flat/open",
                "/api/payment/session",
                "/api/payment/**",
                "/repository/stdtariffs/**",
                "/repository/stdtariffs",
                "/repository/fees/**",
                "/repository/fees",
                "/repository/payments/**",
                "/repository/payments",
                "/repository/houses/**",
                "/repository/houses",
                "/repository/flats/**",
                "/repository/flats",
                "/api/address/house/updateBillingLocked",
                "/api/decision",
                "/api/flat/**",
                "/api/flat/**/*"
        };
        // TODO: when adding URLs to this list, inform client-side developer in order to process 503 error gently in corresponding part of UI
    }

    private String[] urlsUserDeputyGDOperatorKapremontOpkrProjectGatiEstimatePlaneconOfapOknCommunicationPretenseBuildControlCallcenterBankRoles() {
        return new String[]{
                "/repository/flatcomments/search/findByFlatIdOrderByModDateDesc"
        };
    }


    private String[] urlsUserDeputyGDOperatorKapremontOpkrProjectGatiEstimatePlaneconOfapOknCommunicationRolesPretenseBankPutMethod() {
        return new String[]{
                "/api/address/house/comment"
        };
    }

    // KAP-1619 bug fix
    private String[] urlsUserDeputyGDOperatorKapremontOpkrProjectGatiEstimatePlaneconOfapOknCommunicationPretenseCallcenterBankRolesPost() {
        return new String[]{
                "/repository/flatcomments"
        };
    }

    private String[] urlsOperatorUserDeputyGDKapremontOpkrProjectGatiEstimatePlaneconOfapOknPretenseGet() {
        return new String[]{
                "/api/report/repairWorkTender"
        };
    }

    private String[] urlsOperatorBankGet() {
        return new String[]{
                "/api/address/msthouses/search/findHouseIdsByHouseSpecAccountExistsAndBillingEmail",
        };
    }

    private String[] urlsBankCommunicationGetPost() {
        return new String[]{
                "/api/penalties/**",
                "/api/penalties/**/*",
                "/api/penalties/cancelPenalties",
                "/api/penalties/cancelPenaltiesHouse",
                "/api/house/**/cancelFeesHouse"
        };
    }

    private String[] urlsBankCommunicationGet() {
        return new String[]{
                "/api/billing/session/getPenaltyBillingSessionMonth"
        };
    }

    private String[] urlsAdminBankCommunicationCallcenterGetPretenseDeputyGD() {
        return new String[]{
                "/api/flat/pretenseBill"
        };
    }

    private String[] urlsAuctionPostPatch() {
        return new String[]{
                "/api/fond/fondSignatory/**",
//                "/api/fond/fondSignatory/update/**",
//                "/api/fond/fondSignatory/delete/**",
                "/api/edoc/signatoryBaseFond/**",
                "/api/auctionplan/flipActive/**",
                "/api/auctionplan/checkForDuplicates",
                "/api/auctionplan/update/**",
                "/api/auctionplan",
                "/api/auctionplan/reannounce/**",
                "/api/auctioncontract/**",
                "/api/contractor/**",
                "/api/edoc/auction/**",
                "/api/bankguarantee/update/**",
                "/api/bankguarantee/create/",
                "/api/bankguarantee/toggleactivebankguarantee/**",
                "/api/edoc/auctionContractDocType/**",
                "/api/edoc/bankGuarantee/**",
                "/api/auctionContractDocType/update/**",
                "/api/auctionContractDocType/create/",
                "/api/auctionContractDocType/toggleActiveAuctionContractDocType/**",
                "/api/auctionplan/*/protocol/applicationsConsideration",
                "/api/auctionplan/*/protocol/applicationsConsideration/*",
                "/api/auctionplan/protocol/applicationsConsideration/*",
                "/api/auctionplan/*/protocol/auctionHolding",
                "/api/auctionplan/protocol/auctionHolding/*"

        };
    }

    private String[] urlsAuctionOpkrPostPatchDelete() {
        return new String[]{
                "/api/edoc/auction/**",
                "/api/edoc/auctionContractDocType/**",
                "/api/edoc/bankGuarantee/**"
        };
    }

    private String[] urlsAuctionGet() {
        return new String[]{
                "/api/fond/fondSignatory/list",
                "/api/fond/fondSignatory",
                "/api/fond/fondSignatory/view/**",
                "/api/edoc/signatoryBaseFond/**",
                "/api/auctionplan/checkForDuplicates",
                "/api/auctionplan/list",
                "/api/auctionplan/view/**",
                "/api/auctionplan/status",
                "/api/auction/status",
                "/api/auctionplan/reannounced/**",
                "/api/auctioncontract/**",
                "/repository/auctionContractStatuses",
                "/api/contractors/**",
                "/repository/SignatoryBaseTypeRepository",
                "/repository/paymentSecurityTypes",
                "/repository/fondSignatories",
                "/api/auctionplan/generateNumber",
                "/api/contractor/list",
                "/api/auctionplan/setHouseIsOkn",
                "/api/auctionplan/getRepairWorkList",
                "/api/auctionContractDocType/**",
                "/api/bankguarantee/**",
                "/api/auctionplan/*/protocol/applicationsConsideration",
                "/api/auctionplan/*/protocol/auctionHolding",
                "/api/auctonplan/commission/template/groups",
                "/api/auctionplan/*/contractors"
        };
    }

    private String[] urlsUserDeputyGDOperatorKapremontOpkrProjectGatiEstimatePlaneconOfapOknCommunicationPretenseBuildControlAuctionRoles() {
        return new String[]{
                "/api/auctionplan/list/criteria"
        };
    }

    private String[] urlsKapremontOpkrProjectGatiEstimatePlaneconOfapOknBuildControlDeputyGDAuctionRoleGet() {
        return new String[]{
                "/repository/userDepartmentsGlobal/search/findByActiveTrueAndGroupByIdOrderByNameShort",
                "/api/repairwork/permissionglobal",
                "/api/repairwork/permissionglobal/save",
                "/api/repairwork/permission/delete/*"
        };
    }

    private String[] urlsAdminDeputyGDRolePatch() {
        return new String[]{
                "/api/repairwork/permissionglobal/moveToActive/*"
        };
    }

    private String[] urlsAdminCommunicationBankRolePatch() {
        return new String[]{
                "/api/flat/*/updateFlatStartDate",
                "/api/flat/*/updateFlatEndDate"
        };
    }

    private String[] urlsPlaneconRolePostPatchDelete() {
        return new String[]{
                "/api/subsidy/update/**",
                "/api/subsidy/flipActive",
                "/api/edoc/subsidy/**",
                "/api/auctionPayment/saveAuctionPayments",
                "/api/auctionPayment/deleteAuctionPaymentByIds"
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder registry) throws Exception {
        registry.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    /**
     * Need this override for definite bean name to inject:
     */
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(billingInProgressFilter(urlsRestrictedOnBilling()), FilterSecurityInterceptor.class).csrf().disable().authorizeRequests()

                .antMatchers(HttpMethod.GET, "/", "/login", "/logout", "/api/loggedin", /*KAP-871*/"/api/billing/session/inprogress").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/logout").permitAll()

                .antMatchers(HttpMethod.GET, urlsReferences()).authenticated()
                .antMatchers(HttpMethod.GET, urlsWithoutRolesGet()).authenticated()
                .antMatchers(HttpMethod.POST, "/api/location").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/user/**").authenticated()
                .anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                    throws IOException, ServletException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "KAP Unauthorized");
            }
        });
    }

    /**
     * Additional filter to restrict access dynamically based on billing-in-progress status
     *
     * @param restrictUrls URLs to restrict access when billing is in progress (POST,PUT,PATCH,DELETE methods)
     * @return
     */
    private Filter billingInProgressFilter(final String[] restrictUrls) {
        return new Filter() {
            private AntPathMatcher matcher = new AntPathMatcher();

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse resp = (HttpServletResponse) response;
                // check for billing in progress and request types that changes billing data :
                if (billingSessionService.isBillingInProgress() && ((req.getMethod().equals("POST") || req.getMethod().equals("PUT") || req.getMethod().equals("PATCH")
                        || req.getMethod().equals("DELETE")))) {
                    String reqService = req.getRequestURI().substring(StringUtils.length(req.getContextPath()));
                    for (String pattern : restrictUrls) {
                        if (matcher.match(pattern, reqService)) {
                            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Data changes is restricted while billing in progress");
                            return;
                        }
                    }
                }
                chain.doFilter(request, response);
            }

            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                // TODO Auto-generated method stub
            }


            @Override
            public void destroy() {
                // TODO Auto-generated method stub
            }
        };
    }

}