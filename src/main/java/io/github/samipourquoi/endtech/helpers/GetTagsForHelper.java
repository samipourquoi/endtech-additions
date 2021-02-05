package io.github.samipourquoi.endtech.helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.Identifier;

public class GetTagsForHelper {

	   @SuppressWarnings("unchecked")
	public static <T> Collection<Identifier> getTagsForTagGroup(TagGroup<T> tagGroup, T object) {
		List<Identifier> list = Lists.newArrayList();
		Iterator<?> var3 = tagGroup.getTags().entrySet().iterator();

		while(var3.hasNext()) {
			Entry<Identifier, Tag<T>> entry = (Entry<Identifier, Tag<T>>)var3.next();
			if (((Tag<T>)entry.getValue()).contains(object)) {
				list.add(entry.getKey());
			}
		}

		return list;
	}
}
