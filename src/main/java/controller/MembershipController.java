package controller;

import model.Membership;
import service.MembershipService;

import java.util.List;
import java.util.UUID;

public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController() {
        this.membershipService = new MembershipService();
    }

    public void saveMembership(Membership membership) {
        membershipService.saveMembership(membership);
    }

    public Membership getMembershipById(UUID membership_id) {
        return membershipService.getMembershipById(membership_id);
    }

    public List<Membership> getAllMemberships() {
        return membershipService.getAllMemberships();
    }

    public void updateMembership(UUID membership_id, Membership updatedMembership) {
        membershipService.updateMembership(membership_id, updatedMembership);
    }

    public void deleteMembership(UUID membership_id) {
        membershipService.deleteMembership(membership_id);
    }
}
