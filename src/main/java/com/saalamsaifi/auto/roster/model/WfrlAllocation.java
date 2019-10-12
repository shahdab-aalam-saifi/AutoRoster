package com.saalamsaifi.auto.roster.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class WfrlAllocation {
	private static final String SEPARATOR = "::";
	private Team team;
	private Map<String, Integer> groupAllocationRemaining;
	private Map<String, ArrayList<LocalDate>> memberWfrlAllocations;

	public WfrlAllocation(final Team team) {
		this.team = team;

		Collections.shuffle(this.team.getGroups());

		List<Group> groups = this.team.getGroups();

		this.groupAllocationRemaining = new HashMap<>(groups.size());
		this.memberWfrlAllocations = new HashMap<>();

		groups.forEach(group -> {
			groupAllocationRemaining.put(group.getName(), group.getMaxWfrlAllowed());

			Collections.shuffle(group.getMembers());

			List<Member> members = group.getMembers();

			members.forEach(member -> memberWfrlAllocations.put(member.getName(), new ArrayList<>()));
		});
	}

	/**
	 * @return
	 */
	public Map<String, Integer> getGroupAllocationRemaining() {
		return new HashMap<>(this.groupAllocationRemaining);
	}

	/**
	 * @return
	 */
	public Map<String, ArrayList<LocalDate>> getMemberWfrlAllocations() {
		return memberWfrlAllocations;
	}

	/**
	 * @param predicate
	 * @return
	 */
	private List<String> wfrlCandidate(Predicate<Member> predicate) {
		List<String> candidateForAllocation = new ArrayList<>();

		Collections.shuffle(this.team.getGroups());

		List<Group> groups = this.team.getGroups();

		groups.forEach(group -> {
			Collections.shuffle(group.getMembers());

			List<Member> members = group.getMembers();

			members.forEach(member -> {
				if (predicate.test(member)) {
					candidateForAllocation.add(group.getName() + "::" + member.getName());
				}
			});
		});

		return candidateForAllocation;
	}

	/**
	 * @param day
	 * @return
	 */
	public int allocateWfrl(LocalDate day) {
		int unallocatedWfrl = this.team.getMaxWfrlAllowed();

		unallocatedWfrl = allocate(member -> member.isInterested() && !member.getLikes().contains(day.getDayOfWeek())
				&& !member.getDislikes().contains(day.getDayOfWeek()), day, unallocatedWfrl);

		unallocatedWfrl = allocate(member -> member.isInterested() && member.getLikes().contains(day.getDayOfWeek()),
				day, unallocatedWfrl);

		return unallocatedWfrl;
	}

	/**
	 * @param predicate
	 * @param day
	 * @param unallocatedWfrl
	 * @return
	 */
	private int allocate(Predicate<Member> predicate, LocalDate day, int unallocatedWfrl) {
		List<String> candidate = wfrlCandidate(predicate);
		Map<String, Integer> groupAllocations = this.getGroupAllocationRemaining();

		Iterator<String> itr = candidate.listIterator();

		while (itr.hasNext() && unallocatedWfrl > 0) {
			String[] temp = itr.next().split(SEPARATOR);
			String groupName = temp[0];
			String memberName = temp[1];

			int maxWfrlAllowed = groupAllocations.get(groupName);
			List<LocalDate> pAllocations = this.getMemberWfrlAllocations().get(memberName);

			if (maxWfrlAllowed > 0 && pAllocations.size() < this.team.getMaxWfrlAllowed()) {
				if (this.getMemberWfrlAllocations().get(memberName).contains(day.minusDays(1))) {
					continue;
				}
				this.getMemberWfrlAllocations().get(memberName).add(day);
				groupAllocations.put(groupName, --maxWfrlAllowed);
				unallocatedWfrl--;
			}
		}

		return unallocatedWfrl;
	}

}
