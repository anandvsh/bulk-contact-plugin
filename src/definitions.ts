export interface BulkContactPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
