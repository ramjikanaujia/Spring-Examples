package example.infrastructure.jms;

import java.util.UUID;

import com.google.common.base.Objects;

public class JmsTestMessage {

	private UUID uuid = UUID.randomUUID();

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		JmsTestMessage other = (JmsTestMessage) obj;
		return Objects.equal(uuid, other.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(uuid);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(uuid).toString();
	}
}