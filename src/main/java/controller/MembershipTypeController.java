package controller;

import model.MembershipType;
import service.MembershipTypeService;

import java.util.List;
import java.util.UUID;

public class MembershipTypeController {

    private final MembershipTypeService membershipTypeService;

    public MembershipTypeController() {
        this.membershipTypeService = new MembershipTypeService();
    }

    public void saveMembershipType(MembershipType membershipType) {
        membershipTypeService.saveMembershipType(membershipType);
    }

    public MembershipType getMembershipTypeById(UUID membership_type_id) {
        return membershipTypeService.getMembershipTypeById(membership_type_id);
    }

    public List<MembershipType> getAllMembershipTypes() {
        return membershipTypeService.getAllMembershipTypes();
    }

    public void updateMembershipType(UUID membership_type_id, MembershipType updatedMembershipType) {
        membershipTypeService.updateMembershipType(membership_type_id, updatedMembershipType);
    }

    public void deleteMembershipType(UUID membership_type_id) {
        membershipTypeService.deleteMembershipType(membership_type_id);
    }
    public List<MembershipType> listMembershipTypes() {
        return membershipTypeService.getAllMembershipTypes();
    }
}
